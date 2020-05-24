#!/bin/bash

DUNE_BASE="/opt/dune"
DUNE_DIRS="db firmware lib log www"

echo -n "Checking if dune exists... "
if [ -e ${DUNE_BASE}/bin/dune ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 1
fi

echo -n "Creating dirs... "
for dir in ${DUNE_DIRS}; do
  mkdir ${DUNE_BASE}/${dir} 2>/dev/null && echo -n "${dir}... "
done
echo "OK."

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'
if [ $? -eq 0 ]; then
  echo "found!"
  mkdir -p /mnt/storage/dune-log
  grep -iq '/mnt/storage/dune-log' /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for dune-log bind
/mnt/storage/dune-log   /opt/dune/log   none   defaults,bind   0   0
EOF
  mount -a
fi
