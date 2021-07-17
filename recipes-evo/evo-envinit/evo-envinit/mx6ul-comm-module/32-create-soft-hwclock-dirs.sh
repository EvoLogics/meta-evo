#!/bin/sh

SOFT_HWCLOCK_BASE="/opt/soft-hwclock"

echo -n "Checking if soft-hwclock exits... "

if [ -e ${SOFT_HWCLOCK_BASE}/soft-hwclock ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi


echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "found!"

  mkdir -p /mnt/storage/soft-hwclock-data

  grep -iq "/mnt/storage/soft-hwclock-data" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for dune-${dir} bind
/mnt/storage/soft-hwclock-data  /opt/soft-hwclock/data   none   defaults,bind   0   0
EOF

  mount -a
else
  echo "not found!"
fi