#!/bin/sh

ENVINIT_PATH="/sbin/evo-envinit"

mount -o remount,rw /

[ -d ${ENVINIT_PATH} ] || exit
for script in ${ENVINIT_PATH}/*-*.sh; do
	${script}
done

systemctl disable systemd-firstboot.service

sync && mount -o remount,ro /

reboot
