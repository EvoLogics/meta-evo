SUMMARY = "readline feature command line wrapper"
DESCRIPTION = "This package provides a small utility that uses the GNU readline library to allow the editing of keyboard input for any other command."
HOMEPAGE = "http://utopia.knoware.nl/~hlub/uck/rlwrap/"
SECTION = "extended"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"

DEPENDS="readline"

RDEPENDS_${PN}="readline"

inherit autotools binconfig

SRC_URI = "${DEBIAN_MIRROR}/main/r/rlwrap/rlwrap_${PV}.orig.tar.gz \
           file://rlwrap-allow-crooscompile.patch;patch=1          \
    "

SRC_URI[md5sum] = "1e04a9dcc0672b02806cc63a0338176a"
SRC_URI[sha256sum] = "5a70d8469db9d0a6630628f2d5d2972ad16c092400b7fbbdf699693ec0f87e44"

