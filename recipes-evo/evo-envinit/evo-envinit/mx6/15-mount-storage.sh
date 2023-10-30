#!/bin/sh

echo -n "Checking if ssd exists... "
if [ -b /dev/sda1 ]; then
  echo -n "found, changing fstab... "
  mkdir -p /mnt/storage
  cat >> /etc/fstab << EOF

# uncomment this for sata ssd
/dev/sda1            /mnt/storage          auto       defaults  0  0
EOF
  mount -a
else
  echo "not found!"

  echo -n "Checking if sdcard exists... "
  if [ -b /dev/mmcblk1p1 ]; then
    echo -n "found, changing fstab... "
    mkdir -p /mnt/storage
    cat >> /etc/fstab << EOF

# uncomment this for sdcard
/dev/mmcblk1p1       /mnt/storage          auto       defaults  0  0
EOF
    mount -a
  else
    echo "not found!"
  fi
fi
