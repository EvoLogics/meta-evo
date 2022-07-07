#!/bin/sh

DROPBEAR_KEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_KEY_DIR}/dropbear_rsa_host_key"
DROPBEAR_ECDSAKEY="${DROPBEAR_KEY_DIR}/dropbear_ecdsa_host_key"
OPENSSH_KEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_KEY_DIR}/ssh_host_rsa_key"
OPENSSH_ECDSAKEY="${OPENSSH_KEY_DIR}/ssh_host_ecdsa_key"

echo -n "Checking dropbear... "
which dropbearkey > /dev/null && [ -d ${DROPBEAR_KEY_DIR} ]
if [ $? -eq 0 ]; then
  if [ ! -e ${DROPBEAR_RSAKEY} ]; then
    dropbearkey -t rsa -f ${DROPBEAR_RSAKEY} && echo -n "rsa "
  fi

  if [ ! -e ${DROPBEAR_ECDSAKEY} ]; then
    dropbearkey -t ecdsa -f ${DROPBEAR_ECDSAKEY} && echo -n "ecdsa "
  fi
else
  echo "not found!"
fi

echo -n "Checking ssh... "
which ssh-keygen > /dev/null && [ -d ${OPENSSH_KEY_DIR} ]
if [ $? -eq 0 ]; then
  if [ ! -e ${OPENSSH_RSAKEY} ]; then
    ssh-keygen -t rsa -f ${OPENSSH_RSAKEY} -N '' && echo "rsa "
  fi

  if [ ! -e ${OPENSSH_ECDSAKEY} ]; then
    ssh-keygen -t ecdsa -f ${OPENSSH_ECDSAKEY} -N '' && echo "ecdsa "
  fi
else
  echo "not found!"
fi

