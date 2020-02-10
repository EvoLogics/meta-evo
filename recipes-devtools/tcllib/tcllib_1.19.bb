LIC_FILES_CHKSUM="file://license.terms;md5=1686715e5b7e0689a0dec6f1bc6fe2c2"
DESCRIPTION = "Set of pure-Tcl extensions"
HOMEPAGE = "http://core.tcl.tk/tcllib/"
SECTION = "devel"
LICENSE = "BSD"

DEPENDS = "tcl-native"

# TODO: fix pkgIndex.tcl to search only installed tcllib-package-* directory

RDEPENDS_${PN} = "tcl"
SRC_URI = " \
  ${SOURCEFORGE_MIRROR}/project/tcllib/tcllib/${PV}/tcllib-${PV}.tar.gz\
"
PR = "r0"

SRC_URI[md5sum] = "8d3990d01e3fb66480d441d18a7a7d0d"
SRC_URI[sha256sum] = "01fe87cf1855b96866cf5394b6a786fd40b314022714b34110aeb6af545f6a9c"

inherit autotools binconfig lib_package allarch

# tcllib package will be empty with depends from all packages
ALLOW_EMPTY_${PN} = "1"
ALLOW_EMPTY_${PN}-dbg = "0"
ALLOW_EMPTY_${PN}-dev = "0"

PACKAGES =+ "tcllib-common"
FILES_${PN}-common = "${libdir}/${PN}${PV}/pkgIndex.tcl"
RDEPENDS_${PN}-common = "tcl"

FILES_${PN}-package-nns = "${bindir}/nns*"
FILES_${PN}-package-dtplite = "${bindir}/dtplite"
FILES_${PN}-package-page = "${bindir}/page"
FILES_${PN}-package-pt = "${bindir}/pt"
FILES_${PN}-package-docstrip = "${bindir}/tcldocstrip"

python populate_packages_prepend () {
    pn = d.getVar('PN') or ''
    libdir = bb.data.expand('${libdir}/${PN}${PV}', d)

    libdir = bb.data.expand('.${libdir}/${PN}${PV}/', d)
    pkgs = []
    for f in os.listdir(libdir):
        if os.path.isdir(os.path.join(libdir,f)):
            pkg = "%s-package-%s" % (pn, f.replace('_', '-'))
            d.appendVar("FILES_%s" % pkg, " ${libdir}/${PN}${PV}/%s" % f)
            d.appendVar("RDEPENDS_%s" % pkg, " %s-common" % pn)
            d.setVar("DESCRIPTION_%s" % pkg, "%s package %s" % (pkg, f))
            pkgs.append(pkg)

    d.prependVar("PACKAGES", "%s " % (" ".join(pkgs)))
    d.prependVar("RDEPENDS_%s" % pn, "%s " % (" ".join(pkgs)))
}

BBCLASSEXTEND = "native nativesdk"

