#!/bin/sh

SINAPS_BASE="/opt/sinaps"
LINK_DIRS="databases maps log"

echo -n "Checking if sinaps exists... "
if [ -e ${SINAPS_BASE}/bin/sinaps ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'
if [ $? -eq 0 ]; then
  echo "found!"

  for dir in ${LINK_DIRS}; do
    mkdir -p /mnt/storage/sinaps/${dir}
    chown sinaps: /mnt/storage/sinaps/${dir}

    grep -iq "/mnt/storage/sinaps/${dir}" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for sinaps/${dir} bind
/mnt/storage/sinaps/${dir}   /opt/sinaps/${dir}   none   defaults,bind   0   0
EOF
  done

  mount -a
else
  echo "not found!"
fi
