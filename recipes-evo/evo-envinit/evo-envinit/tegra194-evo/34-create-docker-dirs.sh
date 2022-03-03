#!/bin/sh

DOCKER_BASE="/usr/bin/docker"

echo -n "Checking if docker exits... "

if [ -e ${DOCKER_BASE} ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "found!"
  systemctl stop docker

  mkdir -p /var/lib/docker
  mkdir -p /mnt/storage/docker/etc

  grep -iq "/mnt/storage/docker" /etc/fstab || cat >> /etc/fstab << EOF

#uncomment this for docker bind
/mnt/storage/docker/etc         /etc/docker             none    defaults,bind,nofail    0   0
/mnt/storage/docker/var         /var/lib/docker         none    defaults,bind,nofail    0   0
EOF
  mount -a
  systemctl start docker
else
  echo "not found!"
fi