CRYPTSETUP_SUDO_CMD ?= "env "PSEUDO_UNLOAD=1" /usr/bin/sudo env "${CRYPTSETUP_SUDO_ENV}""
CRYPTSETUP_ARGS ?= "--type=plain --key-file=${LUKS_PASS_FILE}"

oe_mkluks () {
	local type=$1
	local sudo_cmd="${CRYPTSETUP_SUDO_CMD}"
	local cryptsetup_cmd="$(which cryptsetup)"

	IMAGE_SIZE=${@d.getVar('LUKS_IMAGE_SIZE')}
	CRYPTSETUP_NAME=$(echo ${IMAGE_NAME}${IMAGE_NAME_SUFFIX} | sed 's/\./-/g')

	if [ -z "$IMAGE_SIZE" ]; then
		IMAGE_SIZE=$ROOTFS_SIZE
	fi

	local COUNT=0
	local MIN_COUNT=516 # Minimum cryptsetup partition (1032 * 512) / 1024
	if [ $IMAGE_SIZE -lt $MIN_COUNT ]; then
		COUNT=$MIN_COUNT
	fi

	if [ -L /dev/mapper/${CRYPTSETUP_NAME} ]; then
		bbwarn "/dev/mapper/${CRYPTSETUP_NAME} exist, close it"
		bbdebug 1 Executing "$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}"
		$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}
	fi

	bbdebug 1 Executing "dd if=/dev/zero of=${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.$type.luks seek=$IMAGE_SIZE count=${COUNT} bs=1024"
	dd if=/dev/zero of=${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.$type.luks seek=$IMAGE_SIZE count=$COUNT bs=1024

	bbdebug 1 Executing "$sudo_cmd $cryptsetup_cmd -q open ${CRYPTSETUP_ARGS} ${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type}.luks ${CRYPTSETUP_NAME}"
	$sudo_cmd $cryptsetup_cmd -q open ${CRYPTSETUP_ARGS} ${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type}.luks ${CRYPTSETUP_NAME}

	if ! $sudo_cmd dd if=${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type} of=/dev/mapper/${CRYPTSETUP_NAME} bs=1024; then
		bbdebug 1 Executing "$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}"
		$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}
		bberror "Fail copy ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type} to /dev/mapper/${CRYPTSETUP_NAME}"
		bbfatal_log "Fail copy ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.${type} to /dev/mapper/${CRYPTSETUP_NAME}"
	fi

	case $type in
		ext?)
			# expand ext[234] for partition size
			if ! $sudo_cmd resize2fs -f /dev/mapper/${CRYPTSETUP_NAME}; then
				bbdebug 1 Executing "$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}"
				$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}
				bberror "Fail resize2fs -f /dev/mapper/${CRYPTSETUP_NAME}"
				bbfatal_log "Fail resize2fs -f /dev/mapper/${CRYPTSETUP_NAME}"
			fi
		;;
	esac

	bbdebug 1 Executing "$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}"
	$sudo_cmd $cryptsetup_cmd close ${CRYPTSETUP_NAME}
}

IMAGE_TYPES += "ext2.luks ext3.luks ext4.luks"
CONVERSIONTYPES += "luks"
CONVERSION_CMD:luks = "oe_mkluks ${type}"
CONVERSION_DEPENDS_luks = "cryptsetup-native e2fsprogs-native"
