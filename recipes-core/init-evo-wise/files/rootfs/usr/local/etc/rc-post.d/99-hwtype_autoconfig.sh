#!/bin/sh

case "$(evo-hw hwrev)" in
    r2-eth)
        if evo-hw set-hw auto | logger -st init; then
            MODE="$(awk '/ubi0:.*rootfs/ && sub(",.*","") {print $4}' < /proc/mounts)"
            mount -o remount,rw /
            chmod -x $0
            mount -o remount,$MODE /
        fi
        ;;
esac
