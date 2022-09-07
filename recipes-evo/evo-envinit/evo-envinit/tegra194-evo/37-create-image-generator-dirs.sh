#!/bin/sh

IMAGE_GENERATOR_BASE="/opt/image-generator/bin/neptus.jar"

echo -n "Checking if image generator exits... "

if [ -e ${IMAGE_GENERATOR_BASE} ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "found!"

  [ ! -d "/mnt/storage/image-generator/log" ] && mkdir -p /mnt/storage/image-generator/log
  [ ! -d "/mnt/storage/image-generator/images" ] && install -d -m 0755 -o ig -g ig /mnt/storage/image-generator/images

  # Check if /mnt/storage/image-generator/conf-plugins doesnt exit
  if [ -d "/mnt/storage/image-generator/conf-plugins" ]; then
    # Create the Director
    mkdir -p /mnt/storage/image-generator/conf-plugins
    # Copy contents
    cp -R /opt/image-generator/conf/plugins/* /mnt/storage/image-generator/conf-plugins
  fi

  grep -iq "/mnt/storage/image-generator/log" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for image-generator bind
/mnt/storage/image-generator/log             /opt/image-generator/log             none    defaults,bind,nofail    0   0
/mnt/storage/image-generator/images          /opt/image-generator/images          none    defaults,bind,nofail    0   0
/mnt/storage/image-generator/conf-plugins    /opt/image-generator/conf/plugins    nonde   defaults,bind,nofail    0   0
EOF
  mount -a

# Create environment file for image-generator
VEHICLE_NAME=$(cat /etc/hostname | sed 's/-tegra//g')

echo "VEHICLE_NAME=${VEHICLE_NAME}" >> /etc/default/image-generator-vars

else
  echo "not found!"
fi
