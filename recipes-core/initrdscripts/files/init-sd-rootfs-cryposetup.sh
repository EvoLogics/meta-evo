#!/bin/sh

/bin/mount -t devtmpfs none /dev
/bin/mount -t proc proc /proc
/bin/mount -t sysfs sysfs /sys

if [ -f /sys/firmware/devicetree/base/sd-rootfs ]; then
    #/usr/sbin/cryptsetup open --type=plain --key-file=/rootfs.pass /dev/mmcblk0p1 sd-rootfs
    /sbin/crypt-open-plain-initramfs /rootfs.pass /dev/mmcblk0p2 sd-rootfs && \
        /bin/mount -o ro /dev/mapper/sd-rootfs /mnt && \
            SD_BOOT=1
fi

if [ -z "$SD_BOOT" ]; then
    /bin/mount -t ubifs -o ro ubi0:sama5d2-roadrunner-evo-rootfs /mnt
fi

# /sbin/init need /dev mounted before it called
/bin/mount -t devtmpfs none /mnt/dev

exec /sbin/switch_root /mnt /sbin/init
