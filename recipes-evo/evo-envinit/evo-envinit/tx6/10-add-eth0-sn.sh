#!/bin/sh

IPHEX=$(cat /sys/class/net/eth0/address | cut -d':' -f6)
IPDEC=$(printf "%d" 0x$IPHEX)
DEV="eth0:sn"
NET="192.168.0"
MASK="255.255.255.0"

echo -n "Adding dev ${DEV} with ip ${NET}.${IPDEC}... "

grep -iqe "${DEV}" /etc/network/interfaces 2>/dev/null
if [ $? -ne 0 ]; then
  cat >> /etc/network/interfaces << EOF && echo "OK."

auto ${DEV}
iface ${DEV} inet static
        address ${NET}.${IPDEC}
        netmask ${MASK}
        network ${NET}.0
        gateway ${NET}.1
EOF
else
  echo "already exists."
fi
