#!/bin/sh

MONIT_ID="/etc/monitid"

which monit > /dev/null || { echo "Monit not found, exiting." && exit 0; }

echo -n "Checking monit id... "
if [ -e ${MONIT_ID} ]; then
  echo "found!"
else
  echo -n "not found, generating... "
  { date && dd if=/dev/urandom bs=512 count=1 2>/dev/null; } | md5sum | awk '{print $1}' > ${MONIT_ID} && echo "ok."
fi
