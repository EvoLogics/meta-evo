require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "Das U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"

PROVIDES = "u-boot"

PV = "v2015.10-rc2+git${SRCPV}"

SRCREV = "15e7c9962827c2474ba655567cf644831d746501"
SRCBRANCH = "master"
SRC_URI = "git://github.com/karo-electronics/karo-tx-uboot.git;branch=${SRCBRANCH} \
	   file://mx6-soc-l2en.patch \
	   file://mx6-clock-div.patch \
           file://0001-duplicate-const.patch \
           file://0002-gcc6plus-fix.patch \
           file://0003-net-Use-packed-structures.patch \
	  "

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*)"
