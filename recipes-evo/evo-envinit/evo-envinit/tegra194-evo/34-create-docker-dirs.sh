#!/bin/sh

DOCKER_BASE="/usr/bin/docker"

echo -n "Checking if docker exits... "

if [ -e ${DOCKER_BASE} ]; then
  echo "Docker Found !!"
else
  echo "Docker not Found"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "Storage Mounted !!"
  # Check if Directory exits. If not create it
  [ ! -d "/mnt/storage/docker/var" ] &&  mkdir -p /mnt/storage/docker/var
  [ ! -d "/mnt/storage/docker/etc" ] &&  mkdir -p /mnt/storage/docker/etc
  # Copy contents of /etc/docker to new location
  cp /etc/docker/* /mnt/storage/docker/etc/

  #Bind /etc/docker
  grep -iq "/mnt/storage/docker/etc" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for /etc/docker bind
/mnt/storage/docker/etc        /etc/docker             none    defaults,bind,nofail    0   0
EOF
  mount -a
else
  echo "Storage Not Mounted !!"
fi