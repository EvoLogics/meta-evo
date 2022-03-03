#!/bin/sh

DETECTOR_BASE="/opt/detector/run.py"

echo -n "Checking if object detector exits... "

if [ -e ${DETECTOR_BASE} ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "found!"

  mkdir -p /mnt/storage/detector-logs

  grep -iq "/mnt/storage/docker" /etc/fstab || cat >> /etc/fstab << EOF

#uncomment this for docker bind
/mnt/storage/detector-logs         /opt/detector/logs             none    defaults,bind,nofail    0   0
EOF
  mount -a
else
  echo "not found!"
fi