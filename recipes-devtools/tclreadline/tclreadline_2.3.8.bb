SUMMARY = "GNU Readline Extension for Tcl/Tk"
DESCRIPTION = "tclreadline adds GNU Readline support to standard Tcl/Tk.\
It provides interactive command editing and history for Tcl shells (this must be\
enabled on a user-by-user basis) and allows the use of GNU Readline\
features in Tcl programs.\
"
HOMEPAGE = "https://tclreadline.sourceforge.net/"
LICENSE = "BSD"
SECTION = "devel"
LIC_FILES_CHKSUM = "file://COPYING;md5=0ed764ffba5b33604dd2f7c917a2a087"

SRC_URI = "https://github.com/flightaware/tclreadline/archive/refs/tags/v${PV}.tar.gz \
           file://0001-Remove-Tk-dependency.patch \
           file://0002-Fix-founding-.so-without-symbolic-links.patch \
           file://0001-Fix-library-search-path-usr-lib-is-unsafe-for-cross-.patch \
           "
SRC_URI[md5sum] = "f9ff7068272befa82d22637737735a8a"
SRC_URI[sha256sum] = "a64e0faed5957b8e1ac16f179948e21cdd6d3b8313590b7ab049a3192ab864fb"

DEPENDS += "tcl readline"
RDEPENDS:${PN} += "tcl readline"

inherit autotools lib_package

TCL_INCLUDE_PATH = ""
TCL_INCLUDE_PATH:class-target = "--with-tcl-includes=${STAGING_INCDIR}/tcl8.6 \
                                 --with-readline-includes=${STAGING_INCDIR}"

EXTRA_OECONF = "--with-tcl=${STAGING_LIBDIR} \
    ${TCL_INCLUDE_PATH}"

FILES:${PN} += "${libdir}/${PN}${PV}/*.tcl \
                ${libdir}/lib${PN}-${PV}.so"
FILES:${PN}-dev = "${includedir}/*.h ${libdir}/lib${PN}.so"

PACKAGES =+ "tclshrl"
FILES:tclshrl = "${bindir}/tclshrl"
EXTRA_OECONF += "--enable-tclshrl"

BBCLASSEXTEND = "native nativesdk"
