#!/bin/sh

# TODO: handle ifdown or existing br0

# that will break docker networking, do not use
# do not query iptables for packet routing
#echo 0 > /proc/sys/net/bridge/bridge-nf-call-iptables

brctl addbr br0
brctl addif br0 eth0
brctl addif br0 eth1

# no additional processing for multicast packets
echo 0 > /sys/devices/virtual/net/br0/bridge/multicast_querier
echo 0 > /sys/devices/virtual/net/br0/bridge/multicast_snooping

# do iptables istead
iptables -I FORWARD -m physdev --physdev-is-bridged -j ACCEPT

# aliasing
ip addr add 10.0.0.8/24 dev br0
ip addr add 192.168.53.198/24 dev br0

# openvpn
#openvpn --mktun --dev tap0
#brctl addif br0 tap0

# promiscuous mode for bridged ifs
# (pre-up in interfaces)
#ip link set eth0 promisc on
#ip link set eth1 promisc on
#ip link set tap0 promisc on

ip link set br0 up
route add -net 172.16.0.0/16 gw 10.0.0.1

touch /tmp/br0.done
