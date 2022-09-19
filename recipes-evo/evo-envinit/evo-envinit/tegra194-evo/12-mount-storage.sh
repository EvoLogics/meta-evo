#!/bin/sh

echo -n "Checking if sdcard exists... "

if [ -b /dev/mapper/SSD ]; then
  echo -n "found, changing fstab... "
  mkdir -p /mnt/storage
  sed -i 's|#/dev/mapper/SSD|/dev/mapper/SSD|' /etc/fstab && echo "OK."
  mount -a
elif [ -b /dev/nvme0n1p1 ]; then
  echo -n "found, changing fstab... "
  mkdir -p /mnt/storage
  sed -i 's|#/dev/nvme0n1p1|/dev/nvme0n1p1|' /etc/fstab && echo "OK."
  mount -a
else
  echo "not found!"
fi
