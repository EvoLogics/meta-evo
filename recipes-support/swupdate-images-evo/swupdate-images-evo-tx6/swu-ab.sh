#!/bin/sh

bd=$(dirname $0)
HWID_FILE="/etc/evohw"

DROPBEAR_KEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_KEY_DIR}/dropbear_rsa_host_key"
DROPBEAR_ECDSAKEY="${DROPBEAR_KEY_DIR}/dropbear_ecdsa_host_key"
OPENSSH_KEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_KEY_DIR}/ssh_host_rsa_key"
OPENSSH_ECDSAKEY="${OPENSSH_KEY_DIR}/ssh_host_ecdsa_key"

if [ -e "${bd}/a-set.sh" ]; then
  source "${bd}/a-set.sh"
  echo "Installing on 'a'"
fi

if [ -e "${bd}/b-set.sh" ]; then
  source "${bd}/b-set.sh"
  echo "Installing on 'b'"
fi

if [ -z "${rootdev}" ]; then
  echo "Not sure where to install, exiting!"
  exit 1
fi

do_preinst()
{
  mkfs.ext4 -q -F ${rootdev}
  ln -s ${bootdev} /dev/new_boot
  ln -s ${rootdev} /dev/new_root
  exit 0
}

do_postinst()
{
  mkdir -p /tmp/new_root
  mount -t auto ${rootdev} /tmp/new_root

  for file in \
    ${HWID_FILE} \
    ${DROPBEAR_RSAKEY} \
    ${DROPBEAR_ECDSAKEY} \
    ${OPENSSH_RSAKEY} \
    ${OPENSSH_ECDSAKEY}; do

    if [ -e ${file} ]; then
      echo "Found ${file}, copying to the new destination"
      cp -v ${file} /tmp/new_root/${file}
    fi
  done

  sync && umount ${rootdev}

  /sbin/abtool -s
  rm -f /dev/new_boot 2>/dev/null
  rm -f /dev/new_root 2>/dev/null
  exit 0
}

echo "$0: $@"

case "$1" in
preinst)
    do_preinst
    ;;
postinst)
    do_postinst
    ;;
*)
    echo "default"
    exit 1
    ;;
esac
