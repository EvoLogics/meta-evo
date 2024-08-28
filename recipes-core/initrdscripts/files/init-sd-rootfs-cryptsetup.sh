#!/bin/sh

/bin/mount -t devtmpfs none /dev
/bin/mount -t proc proc /proc
/bin/mount -t sysfs sysfs /sys

if [ -f /sys/firmware/devicetree/base/rootfs-type ]; then
    read TYPE < /sys/firmware/devicetree/base/rootfs-type
    if [ "$TYPE" = "sd" ]; then
        #/usr/sbin/cryptsetup open --type=plain --key-file=/rootfs.pass /dev/mmcblk0p1 sd-rootfs
        printf "mnt: "
        for i in $(/bin/seq 500); do
            /sbin/cryptsetup-plain-static open /rootfs.pass /dev/mmcblk0p2 sd-rootfs > /dev/null 2>&1 && \
                /bin/mount -o ro /dev/mapper/sd-rootfs /mnt && \
                    SD_BOOT=1 && break
            case $i in
                *0)  printf "[$i] ";;
            esac
            usleep 100
        done
        echo $i
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
