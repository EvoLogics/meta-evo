SUMMARY = "G-Kermit is a Unix program for transferring files with the Kermit protocol"
DESCRIPTION = "G-Kermit is a Unix program for transferring files with the Kermit protocol"
AUTHOR = "Frank da Cruz <fdc@columbia.edu>"
HOMEPAGE = "https://www.kermitproject.org/gkermit.html"
SECTION = "console/network"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=c93c0550bd3173f4504b2cbd8991e50b"

SRC_URI = "https://www.kermitproject.org/ftp/kermit/archives/gku200.tar.gz;subdir=${BP} \
           file://0002-Fixes-100-CPU-usage-problem-during-send.patch \
           file://0003-Fix-non-native-build-issue.patch \
           file://0001-Add-progress-indicator.patch \
           file://0004-Fix-warnings.patch"

SRC_URI[md5sum] = "5a4935fbf2e58237169368720249f012"
SRC_URI[sha256sum] = "3ee95b7b08e2633ff277b541caf053a32b3b4bdcc160a0d319bdf5e60571da68"


DEPENDS_class-target = "gwart-native"

PROVIDES += "gwart"
FILES_gwart = "${bindir}/gwart"

inherit update-alternatives

ALTERNATIVE_${PN} = "kermit"
ALTERNATIVE_LINK_NAME[kermit] = "${bindir}/kermit"
ALTERNATIVE_TARGET[kermit] = "${bindir}/gkermit"
ALTERNATIVE_PRIORITY = "50"

do_configure () {
	:
}

do_compile_class-native () {
	oe_runmake gwart CC="${CC}" CFLAGS="${CFLAGS} -DPOSIX"
}

do_compile_class-target () {
	oe_runmake gkermit CC="${CC}" CFLAGS="${CFLAGS} -DPOSIX"
}

do_install_class-native () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/gwart ${D}/${bindir}
}

do_install_class-target () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/gkermit ${D}/${bindir}
}

BBCLASSEXTEND = "native"
