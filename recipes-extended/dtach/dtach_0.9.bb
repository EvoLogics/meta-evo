SUMMARY = "A program that emulates the detach feature of screen"
DESCRIPTION = "dtach is a tiny program that emulates the detach feature of screen, allowing you\
    to run a program in an environment that is protected from the controlling terminal and attach to it later."
AUTHOR = "Ned T. Crigler <crigler@users.sourceforge.net>"
HOMEPAGE = "http://dtach.sourceforge.net/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SRC_URI[md5sum] = "6dac9c0f96d7d55ea56c01504b23faf6"
SRC_URI[sha256sum] = "32e9fd6923c553c443fab4ec9c1f95d83fa47b771e6e1dafb018c567291492f3"

SRC_URI = "https://sourceforge.net/projects/dtach/files/dtach/${PV}/dtach-${PV}.tar.gz\
           file://0003-reproducibly-remove-date-and-time-macros.patch"

inherit autotools

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/dtach ${D}/${bindir}
}
