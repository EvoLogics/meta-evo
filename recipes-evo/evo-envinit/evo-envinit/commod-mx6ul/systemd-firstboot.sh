#!/bin/sh

DROPBEAR_RSAKEY_DIR="/etc/dropbear"
DROPBEAR_RSAKEY="${DROPBEAR_RSAKEY_DIR}/dropbear_rsa_host_key"
OPENSSH_RSAKEY_DIR="/etc/ssh"
OPENSSH_RSAKEY="${OPENSSH_RSAKEY_DIR}/ssh_host_rsa_key"
ENVINIT_PATH="/sbin/evo-envinit"


if [ -e ${DROPBEAR_RSAKEY} -o -e ${OPENSSH_RSAKEY} ]; then
	echo "Host keys found, boot in normal mode"
	exit 0
fi

mount -o remount,rw /

[ -d ${ENVINIT_PATH} ] || exit
for script in ${ENVINIT_PATH}/*-*.sh; do
	${script}
done

systemctl disable systemd-firstboot.service

sync && mount -o remount,ro /










