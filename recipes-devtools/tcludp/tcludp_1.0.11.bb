# Copyright (C) 2019 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "UDP sockets for Tcl"
HOMEPAGE = "http://sourceforge.net/projects/tcludp"
LICENSE = "MIT"
SECTION = "devel"
DEPENDS += "tcl"

RDEPENDS:${PN} += "tcl"

SRC_URI = "${DEBIAN_MIRROR}/main/t/tcludp/tcludp_1.0.11.orig.tar.gz \
           file://0001-Bump-version-to-1.0.11-in-configure.in.patch \
           file://0002-Fix-L-when-cross-compiling.patch \
           file://0003-Move-TEA-macroses-away-from-aclocal.m4-which-will-be.patch \
           "

SRC_URI[md5sum] = "945ea7afd1df9e46090733ffbfd724a1"
SRC_URI[sha256sum] = "a8a29d55a718eb90aada643841b3e0715216d27cea2e2df243e184edb780aa9d"

LIC_FILES_CHKSUM="file://license.terms;md5=5f94cd289c546cf308271804775ddc10"

S = "${WORKDIR}/${PN}"

TCL_INCLUDE_PATH = ""
TCL_INCLUDE_PATH:class-target = "--with-tclinclude=${STAGING_INCDIR}/tcl8.6"

EXTRA_OECONF += "--with-tcl=${STAGING_LIBDIR} \
                 ${TCL_INCLUDE_PATH}"

inherit autotools lib_package

do_install:append() {
    rmdir ${D}/${bindir}
}

FILES:${PN} = "${libdir}/udp${PV}"
BBCLASSEXTEND = "native nativesdk"
