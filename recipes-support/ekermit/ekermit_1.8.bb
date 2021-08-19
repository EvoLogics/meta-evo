SUMMARY = "EK (Embedded Kermit, E-Kermit) is an implementation of the Kermit file transfer protocol"
DESCRIPTION = "EK (Embedded Kermit, E-Kermit) is an implementation of the Kermit file transfer protocol"
AUTHOR = "Frank da Cruz <fdc@columbia.edu>"
HOMEPAGE = "https://www.kermitproject.org/ek.html"
SECTION = "console/network"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=91cd4b586b2086adc7441b770fc1f977"

SRC_URI = "https://www.kermitproject.org/ftp/kermit/archives/ek18.tar;subdir=${BP} \
           file://0001-Fix-warnings.patch \
           file://0002-Add-handle-LDFLAGS.patch \
           file://0003-Add-i-for-binary-mode-as-in-ckermit.patch"

SRC_URI[md5sum] = "f352e41113ee9b269dc6ad540387d19e"
SRC_URI[sha256sum] = "59c54f3fee05797ae2c3fbb9905cd518938bce86db83675a377532289ea2da1b"

ALLOW_EMPTY_${PN}-dev = "0"

inherit update-alternatives

ALTERNATIVE_${PN} = "kermit"
ALTERNATIVE_LINK_NAME[kermit] = "${bindir}/kermit"
ALTERNATIVE_TARGET[kermit] = "${bindir}/ek"
ALTERNATIVE_PRIORITY = "10"

do_configure () {
	:
}

do_compile () {
	oe_runmake CFLAGS="$CFLAGS -DLinux -DNODEBUG" ek
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/ek ${D}/${bindir}
}
