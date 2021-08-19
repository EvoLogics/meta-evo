FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

DEPENDS += "ncurses-native lz4-native"

# to fix nor flash
SRCREV = "7e44ddb093c3b8d723e16212c9a04cd56c77d165"

# NOTE: when you add new dts, do not forget add to meta-evo/conf/machine/sama5d2-roadrunner-evo.conf
SRC_URI += "\
    file://adc-remove-regulator-hack.patch \
    file://adc-force-set-csnaat.patch \
    file://spi-nor-prepare-for-romboot-on-shutdown.patch \
    file://add-rstc-reset-gpio.patch \
    file://0007-Fix-macb-handle-OOM-Killer-and-do-not-kill-eth-inter.patch \
    file://0001-pinctrl-at91-pio4-fix-gpio-hog-related-boot-issues.patch \
    \
    file://${MACHINE}.dts \
    \
    file://overlays/${MACHINE}-tiny.dts \
    file://overlays/${MACHINE}-tiny-eth.dts \
    file://overlays/${MACHINE}-tiny-rs232.dts \
    file://overlays/${MACHINE}-tiny-rs422.dts \
    file://overlays/${MACHINE}-tiny-rs485.dts \
    file://overlays/${MACHINE}-tiny-can.dts \
    \
    file://overlays/${MACHINE}-r2.dts \
    file://overlays/${MACHINE}-r2-eth.dts \
    file://overlays/${MACHINE}-r2-rs232.dts \
    file://overlays/${MACHINE}-r2-rs422.dts \
    file://overlays/${MACHINE}-r2-rs485.dts \
    file://overlays/${MACHINE}-r2-can.dts \
    \
    file://${MACHINE}_defconfig \
    file://sandbox.cfg \
"

PACKAGES_append = " ${KERNEL_PACKAGE_NAME}-devicetree-compressed"
FILES_${KERNEL_PACKAGE_NAME}-devicetree-compressed = "/${KERNEL_IMAGEDEST}/*.dtb.xz /${KERNEL_IMAGEDEST}/*.dtbo.xz"

do_install_append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
        dtb_base_name=`basename $dtb .$dtb_ext`
        case $dtbf in
            overlays/*)
                install -m 0644 ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext \
                                ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.dtbo
                #mv ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext \
                #   ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.dtbo
                dtb_ext=dtbo
                xz ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext
            ;;
            *)
                xz -k ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext
            ;;
        esac
    done
}
do_deploy_append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
        dtb_base_name=`basename $dtb .$dtb_ext`

        rm ${DEPLOYDIR}/$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext \
           ${DEPLOYDIR}/$dtb_base_name.$dtb_ext \
           ${DEPLOYDIR}/$dtb_base_name-${KERNEL_DTB_LINK_NAME}.$dtb_ext

        case $dtbf in
            overlays/*)
                mv ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext \
                    ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.dtbo

               dtb_ext=dtbo
            ;;
        esac

        install -m 0644 ${D}/${KERNEL_IMAGEDEST}/$dtb_base_name.$dtb_ext ${DEPLOYDIR}/$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext
        ln -sf $dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext ${DEPLOYDIR}/$dtb_base_name.$dtb_ext

		for type in ${KERNEL_IMAGETYPE_FOR_MAKE}; do
			if [ "$type" = "zImage" ] && [ "${KERNEL_DEVICETREE_BUNDLE}" = "1" ]; then
				cat ${D}/${KERNEL_IMAGEDEST}/$type \
					${DEPLOYDIR}/$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext \
					> ${DEPLOYDIR}/$type-$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext.bin
				ln -sf $type-$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext.bin \
					${DEPLOYDIR}/$type-$dtb_base_name-${KERNEL_DTB_LINK_NAME}.$dtb_ext.bin
				if [ -e "${KERNEL_OUTPUT_DIR}/${type}.initramfs" ]; then
					cat ${KERNEL_OUTPUT_DIR}/${type}.initramfs \
						${DEPLOYDIR}/$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext
						>  ${DEPLOYDIR}/${type}-${INITRAMFS_NAME}-$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext.bin
					ln -sf ${type}-${INITRAMFS_NAME}-$dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext.bin \
						${DEPLOYDIR}/${type}-${INITRAMFS_NAME}-$dtb_base_name-${KERNEL_DTB_LINK_NAME}.$dtb_ext.bin
				fi
			fi
		done
	done
}

# boot time 5.8s
#SRC_URI += "file://debug-memory.cfg"
# boot time 7.3s
#SRC_URI += "file://debug-memory-more.cfg"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}*.dts  ${S}/arch/arm/boot/dts/
    cp -r ${WORKDIR}/overlays ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}_defconfig ${WORKDIR}/defconfig
}

do_configure_append() {
    CFG="$(ls ${WORKDIR}/*.cfg 2> /dev/null || true)"
    test -n "$CFG" && cat ${WORKDIR}/*.cfg >> ${B}/.config
    return 0
}
