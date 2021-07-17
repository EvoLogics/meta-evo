#!/bin/sh

FILE=/lib/systemd/network/Bridge.netdev
if test -f "$FILE"; then
    echo "$FILE exists."
    #Create a random MAC Address for Bridge Interface
    MAC=$(hexdump -n3 -e'1/3 "52:54:00" 3/1 ":%02X"' /dev/random; echo "")
    echo "MACAddress=${MAC}" >> $FILE
fi

