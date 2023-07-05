#!/bin/sh

/bin/mount -t devtmpfs none /dev
/bin/mount -t proc proc /proc
/bin/mount -t sysfs sysfs /sys

if [ -f /sys/firmware/devicetree/base/rootfs-type ]; then
    read TYPE < /sys/firmware/devicetree/base/rootfs-type
    if [ "$TYPE" = "sd" ]; then
        #/usr/sbin/cryptsetup open --type=plain --key-file=/rootfs.pass /dev/mmcblk0p1 sd-rootfs
        for i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30; do
            /sbin/cryptsetup-plain-static open /rootfs.pass /dev/mmcblk0p2 sd-rootfs > /dev/null 2>&1 && \
                /bin/mount -o ro /dev/mapper/sd-rootfs /mnt && \
                    SD_BOOT=1 && break
            printf "$i "
            usleep 100
        done
        printf "\n"
    fi
fi

if [ -z "$SD_BOOT" ]; then
    if ! /bin/mount -t ubifs -o ro ubi0:sama5d2-roadrunner-evo-rootfs /mnt > /dev/null 2>&1; then
        printf "Insert SD card to board!\n" >&2
        exit 1
    fi
fi

# /sbin/init need /dev mounted before it called
/bin/mount -t devtmpfs none /mnt/dev

exec /sbin/switch_root /mnt /sbin/init
