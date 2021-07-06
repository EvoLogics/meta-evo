#!/bin/sh

exec > /tmp/lxc-dev-polulate.log 2>&1
set -x
set
grep 'root\|grep' /etc/passwd $LXC_ROOTFS_PATH/etc/shadow
awk -F: '/^root:/{print $2}' $LXC_ROOTFS_PATH/etc/shadow

cp -ap /dev/ttyS?     \
       /dev/watchdog* \
       /dev/ppp       \
       /dev/hwrng     \
       /dev/rtc0      \
          ${LXC_ROOTFS_MOUNT}/dev
