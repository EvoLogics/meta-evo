#!/bin/sh
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
    mkfs.ext4 -F /dev/mmcblk1p4
    check_command_success $?
    exit 0
}

do_postinst()
{
    echo "do_postinst"
    echo "mounting Kernel and Device Tree"
    mount /dev/mmcblk1p3 /boot
    check_command_success $?
    echo "Create Copy of device tree"
    cp /boot/imx6ul-comm-module.dtb /boot/oftree
    check_command_success $?
    echo "Create Copy of kernel"
    cp /boot/zImage-mx6ul-comm-module.bin /boot/zImage
    check_command_success $?
    umount /boot
    check_command_success $?
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
