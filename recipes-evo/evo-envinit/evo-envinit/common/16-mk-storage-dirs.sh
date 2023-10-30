#!/bin/sh

MK_DIRS="core"

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'
if [ $? -eq 0 ]; then
  echo "found!"

  for dir in ${MK_DIRS}; do
    mkdir -p /mnt/storage/${dir}
  done

else
  echo "not found!"
fi
