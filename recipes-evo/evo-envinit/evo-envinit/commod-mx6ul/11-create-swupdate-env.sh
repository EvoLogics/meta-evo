#!/bin/sh

NEXT_PARTITION=$(/sbin/update-util --get-next)

echo "SWUPDATE_EXTRA_ARGS=\"-k /sbin/evo-envinit/evo-updater-public.pem -e stable,${NEXT_PARTITION} -p reboot\"" > /etc/default/swupdate
