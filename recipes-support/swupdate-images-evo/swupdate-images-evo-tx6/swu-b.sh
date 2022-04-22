#!/bin/sh

bootdev=/dev/mmcblk3p2
rootdev=/dev/mmcblk3p4

do_preinst()
{
    mkfs.ext4 -q -F ${rootdev}
    ln -s ${bootdev} /dev/new_boot
    ln -s ${rootdev} /dev/new_root
    exit 0
}

do_postinst()
{
    /sbin/abtool -s
    rm -f /dev/new_boot 2>/dev/null
    rm -f /dev/new_root 2>/dev/null
    exit 0
}

echo "$0: $@"

case "$1" in
preinst)
    do_preinst
    ;;
postinst)
    do_postinst
    ;;
*)
    echo "default"
    exit 1
    ;;
esac
