require lvm2.inc

DEPENDS += "autoconf-archive-native"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    oe_runmake 'DESTDIR=${D}' V=1 -C libdm install
}

do_compile() {
    oe_runmake 'DESTDIR=${D}' -C libdm
}

PACKAGES =+ "${PN}-bin ${PN}-bin-static"

FILES:${PN}-bin = " \
    ${sbindir}/dmsetup \
    ${sbindir}/dmstats \
"

FILES:${PN}-bin-static = " \
    ${sbindir}/dmsetup.static \
    ${sbindir}/dmstats.static \
"

BBCLASSEXTEND = "native nativesdk"
