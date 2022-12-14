#!/bin/sh

echo -n "Marking rootfs as good bootable... "
/sbin/abtool -g && echo "ok." || echo "failed!"
