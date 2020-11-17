#!/bin/sh

NEXT_PARTITION=$(/sbin/update-util --get-next)

echo "SWUPDATE_EXTRA_ARGS=\"-k /etc/ca-certificates/evo-updater-public.pem -e stable,${NEXT_PARTITION} -p reboot\"" > /etc/default/swupdate
