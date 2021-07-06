#!/bin/sh

DROPBEAR_RSAKEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_RSAKEY_DIR}/dropbear_rsa_host_key"
OPENSSH_RSAKEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_RSAKEY_DIR}/ssh_host_rsa_key"

echo -n "Checking dropbear... "
which dropbearkey > /dev/null && [ -d ${DROPBEAR_RSAKEY_DIR} ]
if [ $? -eq 0 ]; then
  dropbearkey -t rsa -f ${DROPBEAR_RSAKEY} && echo "success!"
else
  echo "not found!"
fi

echo -n "Checking ssh... "
which ssh-keygen > /dev/null && [ -d ${OPENSSH_RSAKEY_DIR} ]
if [ $? -eq 0 ]; then
  ssh-keygen -t rsa -f ${OPENSSH_RSAKEY} -N '' && echo "success!"
else
  echo "not found!"
fi

