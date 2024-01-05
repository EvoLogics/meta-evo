#!/bin/sh

DROPBEAR_KEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_KEY_DIR}/dropbear_rsa_host_key"
DROPBEAR_ECDSAKEY="${DROPBEAR_KEY_DIR}/dropbear_ecdsa_host_key"
OPENSSH_KEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_KEY_DIR}/ssh_host_rsa_key"
OPENSSH_ECDSAKEY="${OPENSSH_KEY_DIR}/ssh_host_ecdsa_key"

if command -v ssh-keygen > /dev/null 2>&1; then
  echo "Using ssh-keygen"
  GEN="ssh-keygen -N ''"
elif command -v dropbearkey > /dev/null 2>&1; then
  echo "Using dropbearkey"
  GEN="dropbearkey"
else
  echo "No key generator found!"
  # exit, but do not fail the whole init
  exit 0
fi

genkey()
{
  if [ -e ${2} ]; then
    return
  fi

  ${GEN} -t ${1} -f ${2} && echo -n "${1} "

  if [ "${GEN}" == "dropbearkey" ]; then
    ${GEN} -y -f ${2} ${3} 2>/dev/null | grep -E 'ssh-|ecdsa-' > ${2}.pub
  fi
}

echo -n "Checking dropbear... "
if [ -d ${DROPBEAR_KEY_DIR} ]; then
  genkey rsa ${DROPBEAR_RSAKEY}
  genkey ecdsa ${DROPBEAR_ECDSAKEY}
else
  echo "not found!"
fi

echo -n "Checking ssh... "
if [ -d ${OPENSSH_KEY_DIR} ]; then
  genkey rsa ${OPENSSH_RSAKEY}
  genkey ecdsa ${OPENSSH_ECDSAKEY}
else
  echo "not found!"
fi
