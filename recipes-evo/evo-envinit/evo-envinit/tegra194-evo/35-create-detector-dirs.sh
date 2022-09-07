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
  echo "Storage Mounted !"
  [ ! -d "/mnt/storage/detector/logs" ] &&  mkdir -p /mnt/storage/detector/logs
  [ ! -d "/mnt/storage/detector/updates" ] &&  install -d -m 0755 -o neptus -g neptus /mnt/storage/detector/updates
  chown
else
  echo "Storage not found!"
fi
