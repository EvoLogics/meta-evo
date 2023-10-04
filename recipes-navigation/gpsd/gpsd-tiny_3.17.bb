SUMMARY = "A TCP/IP Daemon simplifying the communication with GPS devices"
SECTION = "console/network"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d217a23f408e91c94359447735bc1800"
#DEPENDS = "dbus dbus-glib ncurses python libusb1 chrpath-replacement-native pps-tools"
DEPENDS = "ncurses libusb1 chrpath-replacement-native pps-tools"
PROVIDES = "virtual/gpsd"

CONFLICT = "gpsd"
RCONFLICT = "gpsd"
RCONFLICTS:virtual/gpsd = "gpsd"

EXTRANATIVEPATH += "chrpath-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/gpsd-tiny-3.17:"

ON="${@'${BP}'.replace('-tiny','')}"
S = "${WORKDIR}/${ON}"
SRC_URI = "${SAVANNAH_GNU_MIRROR}/${@'${BPN}'.replace('-tiny','')}/${ON}.tar.gz \
    file://0001-SConstruct-prefix-includepy-with-sysroot-and-drop-sy.patch \
    file://0004-SConstruct-disable-html-and-man-docs-building-becaus.patch \
    file://0001-include-sys-ttydefaults.h.patch \
    file://allow-work-with-socat-pty.patch \
"

SRC_URI:append_mx6ul-comm-module = "   \
    file://gpsd.commod                 \ 
    file://gpsd.service                \
"


SRC_URI[md5sum] = "e0cfadcf4a65dfbdd2afb11c58f4e4a1"
SRC_URI[sha256sum] = "68e0dbecfb5831997f8b3d6ba48aed812eb465d8c0089420ab68f9ce4d85e77a"

inherit scons update-rc.d systemd update-alternatives

INITSCRIPT_PACKAGES = "gpsd-tiny-conf"
INITSCRIPT_NAME = "gpsd"
INITSCRIPT_PARAMS = "defaults 35"

SYSTEMD_OESCONS = "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false',d)}"

export STAGING_INCDIR
export STAGING_LIBDIR

EXTRA_OESCONS = " \
    sysroot=${STAGING_DIR_TARGET} \
    libQgpsmm='false' \
    debug='true' \
    strip='false' \
    chrpath='yes' \
    qt='no' \
    aivdm='no' \
    ashtech='no' \
    bluez='no' \
    ashtech='no' \
    cw='no' \
    earthmate='no' \
    evermore='no' \
    fury='no' \
    fv18='no' \
    garmin='no' \
    garmintxt='no' \
    geostar='no' \
    ipv6='no' \
    itrax='no' \
    libgpsmm='no' \
    mtk3301='no' \
    navcom='no' \
    oceanserver='no' \
    oncore='no' \
    passthrough='no' \
    pps='yes' \
    sirf='yes' \
    superstar2='no' \
    tnt='no' \
    tripmate='no' \
    ublox='yes' \
    python='no' \
    systemd='${SYSTEMD_OESCONS}' \
    libdir='${libdir}' \
    ${PACKAGECONFIG_CONFARGS} \
"
# this cannot be used, because then chrpath is not found and only static lib is built
# target=${HOST_SYS}

do_compile:prepend() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"
    export STAGING_PREFIX="${STAGING_DIR_HOST}/${prefix}"
    export LINKFLAGS="${LDFLAGS}"
}

do_install() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"
    export STAGING_PREFIX="${STAGING_DIR_HOST}/${prefix}"
    export LINKFLAGS="${LDFLAGS}"

    export DESTDIR="${D}"
    # prefix is used for RPATH and DESTDIR/prefix for instalation
    ${STAGING_BINDIR_NATIVE}/scons prefix=${prefix} install ${EXTRA_OESCONS}|| \
      bbfatal "scons install execution failed."
}

do_install:append() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/packaging/deb/etc_init.d_gpsd ${D}/${sysconfdir}/init.d/gpsd
    install -d ${D}/${sysconfdir}/default
    install -m 0644 ${S}/packaging/deb/etc_default_gpsd ${D}/${sysconfdir}/default/gpsd.default

    #support for udev
    install -d ${D}/${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/gpsd.rules ${D}/${sysconfdir}/udev/rules.d/
    install -d ${D}${base_libdir}/udev/
    install -m 0755 ${S}/gpsd.hotplug ${D}${base_libdir}/udev/

    #support for python
    # install -d ${D}/${PYTHON_SITEPACKAGES_DIR}/gps
    # install -m 755 ${S}/gps/*.py ${D}/${PYTHON_SITEPACKAGES_DIR}/gps

    #support for systemd
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${S}/systemd/gpsd.service ${D}${systemd_unitdir}/system/${BPN}.service
    install -m 0644 ${S}/systemd/gpsdctl@.service ${D}${systemd_unitdir}/system/${BPN}ctl@.service
    install -m 0644 ${S}/systemd/gpsd.socket ${D}${systemd_unitdir}/system/${BPN}.socket
}

do_install:append_mx6ul-comm-module(){
    install -m 0644 ${WORKDIR}/gpsd.commod ${D}/${sysconfdir}/default/gpsd.commod
    install -m 0644 ${WORKDIR}/gpsd.service ${D}${systemd_unitdir}/system/${BPN}.service
}

PACKAGES =+ "libgps lib${BPN} ${BPN}-udev ${BPN}-conf ${BPN}-gpsctl"

FILES:${PN}-dev += "${libdir}/pkgconfdir/libgpsd.pc ${libdir}/pkgconfdir/libgps.pc \
                    ${libdir}/libQgpsmm.prl"

RDEPENDS:${PN} = "${BPN}-gpsctl"
RRECOMMENDS:${PN} = "${BPN}-conf ${BPN}-udev gpsd-machine-conf"

SUMMARY:${BPN}-udev = "udev relevant files to use gpsd hotplugging"
FILES:${BPN}-udev = "${base_libdir}/udev ${sysconfdir}/udev/*"
RDEPENDS:${BPN}-udev += "udev gpsd-tiny-conf"

SUMMARY:lib${BPN} = "C service library used for communicating with gpsd"
FILES:lib${BPN} = "${libdir}/libgpsd.so.*"

SUMMARY:libgps = "C service library used for communicating with gpsd"
FILES:libgps = "${libdir}/libgps.so.*"

SUMMARY:${BPN}-conf = "gpsd configuration files and init scripts"
FILES:${BPN}-conf = "${sysconfdir}"
CONFFILES:${BPN}-conf = "${sysconfdir}/default/gpsd.default"

SUMMARY:${BPN}-gpsctl = "Tool for tweaking GPS modes"
FILES:${BPN}-gpsctl = "${bindir}/gpsctl"

RPROVIDES:${PN} += "${PN}-systemd"
RREPLACES:${PN} += "${PN}-systemd"
RCONFLICTS:${PN} += "${PN}-systemd"
SYSTEMD_SERVICE:${PN} = "${BPN}.socket ${BPN}ctl@.service"
SYSTEMD_SERVICE:${PN}_mx6ul-comm-module = "${BPN}.socket ${BPN}.service  ${BPN}ctl@.service"


ALTERNATIVE:${PN} = "gpsd-defaults"
ALTERNATIVE_LINK_NAME[gpsd-defaults] = "${sysconfdir}/default/gpsd"
ALTERNATIVE_TARGET[gpsd-defaults] = "${sysconfdir}/default/gpsd.default"