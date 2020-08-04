#!/bin/sh

# Stop ntpd.
/etc/init.d/ntpd stop

# Set time.
SERVER=$(grep -m1 -i server /etc/ntp.conf | awk '{print $2}')
ntpd -q -g -n -d -p ${SERVER}

# Start ntpd.
/etc/init.d/ntpd start
