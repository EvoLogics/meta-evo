#!/bin/sh

DBAGENT_BASE="/opt/db-agent/run.py"

echo -n "Checking if docker exits... "

if [ -e ${DBAGENT_BASE} ]; then
  echo "DB-Agent Found !!"
else
  echo "DB-Agent not Found"
  exit 0
fi

echo -n "Checking if storage mounted... "
mount | grep -qi '/mnt/storage'

if [ $? -eq 0 ]; then
  echo "Storage Mounted !!"
  # Check if Directory exits. If not create it
  [ ! -d "/mnt/storage/db-agent/logs" ] &&  mkdir -p /mnt/storage/db-agent/logs
  [ ! -d "/mnt/storage/db-agent/config" ] &&  mkdir -p /mnt/storage/db-agent/config


  # Copy Contents of config to /mnt/storage/db-agent/config
  cp /opt/db-agent/config/* /mnt/storage/db-agent/config/

  #Bind /etc/docker
  grep -iq "/mnt/storage/db-agent/logs" /etc/fstab || cat >> /etc/fstab << EOF

# uncomment this for /mnt/storage/db-agent bind
/mnt/storage/db-agent/logs            /opt/db-agent/logs             none    defaults,bind,nofail    0   0
/mnt/storage/db-agent/config          /opt/db-agent/config           none    defaults,bind,nofail    0   0
EOF
  mount -a
else
  echo "Storage Not Mounted !!"
fi
