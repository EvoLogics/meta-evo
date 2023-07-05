SUMMARY = "lgpio is a library for Linux Single Board Computers (SBC) which allows control of the General Purpose Input Outputs (GPIO)"
DESCRIPTION = "lgpio is a library for Linux Single Board Computers (SBC) which allows control of the General Purpose Input Outputs (GPIO)"
AUTHOR = "Maksym Komar <komar@evologics.de>"

LICENSE = "UNLICENCE"
NO_GENERIC_LICENSE[UNLICENCE] = "UNLICENCE"
LIC_FILES_CHKSUM = "file://UNLICENCE;md5=61287f92700ec1bdf13bc86d8228cd13"

SECTION = "utils"

SRC_URI = "git://github.com/joan2937/lg.git;protocol=https"

# Last commit before changing to using gpiochip API v2,
# introduced in linux v5.10. Last commit before v0.2 :)
SRCREV = "9ebaf583b8647d5531a6d2d797cb27d4aefb6d1a"
PV .= "+git${SRCPV}"

DEPENDS  = "swig-native"
DEPENDS += "python python-setuptools-native"
DEPENDS += "python3 python3-setuptools-native"


S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CC='${CC}'"
EXTRA_OEMAKE += "CROSS_PREFIX=${TARGET_PREFIX}"
# we don't need to strip, it will be stripped by build system
EXTRA_OEMAKE += "STRIP=echo"
EXTRA_OEMAKE += "PYINSTALLARGS='--root=$(DESTDIR) --prefix=${prefix}'"
EXTRA_OEMAKE += "PYBUILDARGS='--include-dirs=$(DESTDIR)$(includedir) --library-dirs=$(DESTDIR)$(libdir):${S}'"
TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN}-python2 = "${libdir}/python2*/*"
FILES_${PN}-python3 = "${libdir}/python3*/*"

PACKAGES =+ " ${PN}-python2 ${PN}-python3"

do_configure() {
    :
}

do_install() {
    oe_runmake install DESTDIR=${D} prefix=${prefix}
}

inherit pythonnative python3native
