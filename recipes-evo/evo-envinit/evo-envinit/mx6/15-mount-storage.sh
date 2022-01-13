#!/bin/sh

echo -n "Checking if ssd exists... "
if [ -b /dev/sda1 ]; then
  echo -n "found, changing fstab... "
  mkdir -p /mnt/storage
  sed -i 's|#/dev/sda1|/dev/sda1|' /etc/fstab && echo "OK."
  mount -a
else
  echo "not found!"

  echo -n "Checking if sdcard exists... "
  if [ -b /dev/mmcblk1p1 ]; then
    echo -n "found, changing fstab... "
    mkdir -p /mnt/storage
    sed -i 's|#/dev/mmcblk1p1|/dev/mmcblk1p1|' /etc/fstab && echo "OK."
    mount -a
  else
    echo "not found!"
  fi
fi
