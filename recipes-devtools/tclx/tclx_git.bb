SUMMARY = "Extended Tcl (TclX) - shared library"
DESCRIPTION = "Extended Tcl (TclX), is a set of extensions to Tcl, the Tool Command \
Language. TclX provides additional interfaces to the operating system, and adds many \
new programming constructs, text manipulation tools, and debugging tools. \
"
LICENSE = "BSD"
SECTION = "devel"
HOMEPAGE = "http://tclx.sourceforge.net/"
LIC_FILES_CHKSUM = "file://license.terms;md5=d1b75cd3cd65de13adee2b067107a694"

SRC_URI = "git://github.com/flightaware/tclx;protocol=https \
           file://0001-Allow-cross-compiling.patch \
           file://0002-Fix-library-search-path-usr-lib-is-unsafe-for-cross-.patch \
           "

BPV = "8.6"
PV = "${BPV}+git${SRCPV}"
SRCREV = "v8.6.2"

S = "${WORKDIR}/git"

DEPENDS += "tcl"
RDEPENDS:${PN} += "tcl"

inherit autotools lib_package

TCL_INCLUDE_PATH = ""
TCL_INCLUDE_PATH:class-target = "--with-tclinclude=${STAGING_INCDIR}/tcl${BPV}"

EXTRA_OECONF = "--with-tcl=${STAGING_LIBDIR} \
    --enable-threads \
    --libdir=${libdir} \
    ${TCL_INCLUDE_PATH}"

FILES:${PN} += "${libdir}/${PN}${BPV}/*.tcl \
                ${libdir}/${PN}${BPV}/lib${PN}${BPV}.so \
                ${bindir}"

BBCLASSEXTEND = "native nativesdk"
