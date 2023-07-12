#!/bin/sh

DROPBEAR_KEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_KEY_DIR}/dropbear_rsa_host_key"
DROPBEAR_ECDSAKEY="${DROPBEAR_KEY_DIR}/dropbear_ecdsa_host_key"
OPENSSH_KEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_KEY_DIR}/ssh_host_rsa_key"
OPENSSH_ECDSAKEY="${OPENSSH_KEY_DIR}/ssh_host_ecdsa_key"
ZEROTIER_DIRECTORY="/opt/zerotier-one/creds"

check_command_success()
{
    if [ $1 -eq 0 ]; then
        echo "Success"
    else
        exit 1
    fi
}


do_preinst()
{
    echo "do_preinst"
    #make ext4 file system
    mkfs.ext4 -F /dev/mmcblk1p2
    check_command_success $?
    exit 0
}

do_postinst()
{
    echo "do_postinst"
    echo "mounting Kernel and Device Tree"
    mount /dev/mmcblk1p1 /boot
    check_command_success $?
    echo "Create Copy of device tree"
    cp /boot/imx6ul-comm-module-mx6ul-comm-module.dtb /boot/oftree
    check_command_success $?
    echo "Create Copy of kernel"
    cp /boot/zImage-mx6ul-comm-module.bin /boot/zImage
    check_command_success $?
    umount /boot
    check_command_success $?
    mkdir -p /tmp/new_root
    check_command_success $?
    mount -t auto /dev/mmcblk1p2 /tmp/new_root
    check_command_success $?
    for file in \
      ${DROPBEAR_RSAKEY} \
      ${DROPBEAR_ECDSAKEY} \
      ${OPENSSH_RSAKEY} \
      ${OPENSSH_ECDSAKEY}; do

      if [ -e ${file} ]; then
        echo "Found ${file}, copying to the new destination"
        cp -v ${file} /tmp/new_root${file}
      fi
    done

    if [ -d ${ZEROTIER_DIRECTORY} ]; then
      mkdir -p /tmp/new_root${ZEROTIER_DIRECTORY}
      echo "Found ${ZEROTIER_DIRECTORY}, copying to the new destination"
      cp -R ${ZEROTIER_DIRECTORY}/* /tmp/new_root${ZEROTIER_DIRECTORY}/
    fi

    sync && umount /dev/mmcblk1p2

    /sbin/update-util -sa
    exit 0
}

echo "$0: $@"
set

case "$1" in
preinst)
    echo "call do_preinst"
    do_preinst
    ;;
postinst)
    echo "call do_postinst"
    do_postinst
    ;;
*)
    echo "default"
    exit 1
    ;;
esac
