SUMMARY = "AVRDUDE is a utility to download/upload/manipulate the ROM and EEPROM contents of AVR microcontrollers using the in-system programming technique (ISP)."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4f51bb496ef8872ccff73f440f2464a8"

DEPENDS = " \
    bison-native \
    flex \
    elfutils \
    libusb1 \
    libftdi \
    hidapi \
    readline \
"

inherit cmake gettext

SRC_URI = "git://github.com/avrdudes/avrdude.git;protocol=https;branch=main \
    file://0001-Fix-for-older-cmake.patch \
    file://0002-hack-to-keep-gpio-spi-config.patch \
    "

SRCREV = "2e0be1e1aedfc10c904712bddda37e36356e7a66"
S = "${WORKDIR}/git"
PV = "7.1+git${SRCPV}"

EXTRA_OECMAKE += " -DHAVE_LINUXGPIO=YES -DHAVE_LINUXSPI=YES "
