LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=31bdb24d4c5a6a905a17e96c73684243"

SRC_URI = "${SOURCEFORGE_MIRROR}/libcsv/libcsv/libcsv-${PV}/libcsv-${PV}.tar.gz"

SRC_URI[md5sum] = "d3307a7bd41d417da798cd80c80aa42a"
SRC_URI[sha256sum] = "d9c0431cb803ceb9896ce74f683e6e5a0954e96ae1d9e4028d6e0f967bebd7e4"

inherit autotools binconfig

