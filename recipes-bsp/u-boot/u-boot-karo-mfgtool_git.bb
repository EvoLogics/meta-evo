require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-karo_${PV}.inc
require recipes-bsp/u-boot/u-boot-mfgtool.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/u-boot-karo:"
