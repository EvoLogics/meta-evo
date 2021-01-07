#!/bin/sh

echo -n "Checking if sdcard exists... "
if [ -b /dev/mmcblk0p1 ]; then
  echo -n "found, changing fstab... "
  mkdir -p /mnt/storage
  sed -i 's|#/dev/mmcblk0p1|/dev/mmcblk0p1|' /etc/fstab && echo "OK."
  mount -a
else
  echo "not found!"
fi
