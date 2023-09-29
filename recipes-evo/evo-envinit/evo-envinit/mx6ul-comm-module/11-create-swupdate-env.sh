#!/bin/sh

NEXT_PARTITION=$(/sbin/update-util --get-next)

echo "SWUPDATE_EXTRA_ARGS=\"--ca-path /etc/ca-certificates/evo-ca-root.crt -e stable,${NEXT_PARTITION} -p reboot\"" > /etc/default/swupdate
