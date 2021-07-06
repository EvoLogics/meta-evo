#!/bin/sh

DUNE_BASE="/opt/dune"
DUNE_DIRS="db firmware lib log www"
LINK_DIRS="db log"

echo -n "Checking if dune exists... "
if [ -e ${DUNE_BASE}/bin/dune ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
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

  for dir in ${LINK_DIRS}; do
    mkdir -p /mnt/storage/dune-${dir}

    grep -iq "/mnt/storage/dune-${dir}" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for dune-${dir} bind
/mnt/storage/dune-${dir}   /opt/dune/${dir}   none   defaults,bind   0   0
EOF
  done

  mount -a
else
  echo "not found!"
fi
