#!/bin/sh

bd=$(dirname $0)
HWID_FILE="/etc/evohw"

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
  if [ -e ${HWID_FILE} ]; then
    echo "Found HWID file, copying to the new destination"
    mkdir -p /tmp/new_root
    mount -t auto ${rootdev} /tmp/new_root && \
    cp -v ${HWID_FILE} /tmp/new_root/${HWID_FILE}
    sync && umount ${rootdev}
  fi

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
