#!/bin/sh
do_preinst()
{
    echo "do_preinst"
    #make ext4 file system
    mkfs.ext4 -F /dev/mmcblk1p2
    exit 0
}

do_postinst()
{
    echo "do_postinst"
    echo "mounting Kernel and Device Tree"
    mount /dev/mmcblk1p1 /boot
    echo "Create Copy of device tree"
    cp /boot/imx6ul-comm-module.dtb /boot/oftree
    echo "Create Copy of kernel"
    cp /boot/zImage-mx6ul-comm-module.bin /boot/zImage
    umount /boot
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
