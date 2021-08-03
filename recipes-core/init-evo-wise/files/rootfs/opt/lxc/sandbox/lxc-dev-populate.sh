#!/bin/sh

cp -ap /dev/ttyS?     \
       /dev/watchdog* \
       /dev/ppp       \
       /dev/hwrng     \
       /dev/rtc0      \
          ${LXC_ROOTFS_MOUNT}/dev
