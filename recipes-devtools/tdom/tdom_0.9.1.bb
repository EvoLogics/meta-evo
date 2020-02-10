# Copyright (C) 2019 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Fast XML/DOM/XPath/XSLT extension for Tcl written in C"
HOMEPAGE = "http://tdom.github.io/"
LICENSE = "MPL-1.1"
SECTION = "devel"
DEPENDS += "tcl gumbo-parser"

RDEPENDS_${PN} += "tcl"

SRC_URI = "${DEBIAN_MIRROR}/main/t/tdom/tdom_0.9.1.orig.tar.gz \
           file://0001-Fix-L-when-cross-compiling.patch \
           "

SRC_URI[md5sum] = "53d030649acd82e01720bbe82b3bf0b1"
SRC_URI[sha256sum] = "3b1f644cf07533fe4afaa8cb709cb00a899d9e9ebfa66f4674aa2dcfb398242c"

LIC_FILES_CHKSUM="file://LICENSE;md5=4673aaff544d4c9b9a521cb8e0860bfb"

S = "${WORKDIR}/${BPN}-${PV}"

TCL_INCLUDE_PATH = ""
TCL_INCLUDE_PATH_class-target = "--with-tclinclude=${STAGING_INCDIR}/tcl8.6"

EXTRA_OECONF += "--with-tcl=${STAGING_LIBDIR} \
                 --enable-html5 \
                 --enable-symbols \
                 --enable-tdomalloc \
                 --enable-lessns \
                 ${TCL_INCLUDE_PATH}"

# Need to move TDOM_* macroses from aclocal.m4 to m4/tdom.m4 (or something like that, cos ./tdom.m4 exist)
#                 --enable-unknown \
#
inherit autotools lib_package pkgconfig

do_install_append() {
    rmdir ${D}/${bindir}
}

FILES_${PN} = "${libdir}/tdom*"
FILES_${PN}-dev = "${includedir}/tdom.h"
FILES_${PN}-staticdev = "${libdir}/tdom${PV}/*.a ${includedir}/tdom.h"
