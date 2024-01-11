#!/bin/sh

brctl delif br0 eth0
brctl delif br0 eth1
ip link set br0 down
brctl delbr br0

rm -f /tmp/br0.done
