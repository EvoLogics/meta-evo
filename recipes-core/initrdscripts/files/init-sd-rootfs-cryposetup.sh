#!/bin/sh

/bin/mount -t devtmpfs none /dev
/bin/mount -t proc proc /proc
/bin/mount -t sysfs sysfs /sys

#/usr/sbin/cryptsetup open --type=plain --key-file=/rootfs.pass /dev/mmcblk0p1 sd-rootfs
/sbin/crypt-open-plain-initramfs /rootfs.pass /dev/mmcblk0p1 sd-rootfs
/bin/mount /dev/mapper/sd-rootfs /mnt

# /sbin/init need /dev mounted before it called
/bin/mount -t devtmpfs none /mnt/dev

exec /sbin/switch_root /mnt /sbin/init
