# Break circular dependencies, only populate sysroot (header,
# libraries) to other recipe for compiling, recipe lvm2
# generates package libdevmapper
require lvm2.inc

DEPENDS += "autoconf-archive-native"

# need for static
#DEPENDS += "util-linux"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    oe_runmake 'DESTDIR=${D}' V=1 -C libdm install
}

do_compile() {
    oe_runmake 'DESTDIR=${D}' -C libdm
}
#
## FIXME: not yet work complile libdevmapper statically
## https://gitlab.com/cryptsetup/cryptsetup/-/issues/386
## see lvm2.inc also
#
#EXTRA_OECONF += "--enable-static_link \
#                --disable-dmeventd"
#
## Do not generate package libdevmapper
#DISABLE_STATIC = ""
#FILES_{PN}-staticdev = "${libdir}/*.a"
#PACKAGES = "{PN}-staticdev"
PACKAGES = ""

BBCLASSEXTEND = "native nativesdk"
