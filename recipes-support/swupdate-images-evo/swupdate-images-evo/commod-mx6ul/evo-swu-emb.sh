#!/bin/sh
#example type = "shellscript";

do_preinst()
{
    echo "do_preinst"
    exit 0
}

do_postinst()
{
    echo "do_postinst"
    exit 0
}

echo "$0: $@"
set

case "$1" in
preinst)
    echo "call do_preinst"
    do_preinst
    ;;
postinst)
    echo "call do_postinst"
    do_postinst
    ;;
*)
    echo "default"
    exit 1
    ;;
esac
