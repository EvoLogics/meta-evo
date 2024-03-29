require lvm2.inc

SRC_URI += " \
            file://0001-fix-command-bin-findmnt-bin-lsblk-bin-sort-not-found.patch \
           "

DEPENDS += "autoconf-archive-native"

inherit multilib_script

MULTILIB_SCRIPTS = "${PN}:${sysconfdir}/lvm/lvm.conf"

CACHED_CONFIGUREVARS += "MODPROBE_CMD=${base_sbindir}/modprobe"

do_install_append() {
    # Install machine specific configuration file
    install -d ${D}${sysconfdir}/lvm
    install -m 0644 ${WORKDIR}/lvm.conf ${D}${sysconfdir}/lvm/lvm.conf
    sed -i -e 's:@libdir@:${libdir}:g' ${D}${sysconfdir}/lvm/lvm.conf
    # We don't want init scripts/systemd units for native SDK utilities
    if [ "${PN}" != "nativesdk-lvm2" ]; then
        if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
            oe_runmake 'DESTDIR=${D}' install install_systemd_units
            sed -i -e 's:/usr/bin/true:${base_bindir}/true:g' ${D}${systemd_system_unitdir}/blk-availability.service
        else
            oe_runmake 'DESTDIR=${D}' install install_initscripts
            mv ${D}${sysconfdir}/rc.d/init.d ${D}${sysconfdir}/init.d
            rm -rf ${D}${sysconfdir}/rc.d
        fi
    fi
}

PACKAGE_BEFORE_PN = "${PN}-scripts ${PN}-udevrules"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "${@bb.utils.contains('PACKAGECONFIG', 'dmeventd', 'lvm2-monitor.service dm-event.socket dm-event.service', '', d)} \
                         blk-availability.service"
SYSTEMD_AUTO_ENABLE = "disable"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OECONF_append_class-nativesdk = " --with-confdir=${sysconfdir}"

DEPENDS += "util-linux"
LVM2_PACKAGECONFIG_append_class-target = " \
    udev \
"
PACKAGECONFIG[udev] = "--enable-udev_sync --enable-udev_rules --with-udevdir=${nonarch_base_libdir}/udev/rules.d,--disable-udev_sync --disable-udev_rules,udev,${PN}-udevrules"

DEPENDS =+ "libdevmapper"

FILES_${PN} += " \
    ${libdir}/device-mapper/*.so \
    ${systemd_system_unitdir}/lvm2-pvscan@.service \
"

FILES_${PN}-scripts = " \
    ${sbindir}/blkdeactivate \
    ${sbindir}/fsadm \
    ${sbindir}/lvmconf \
    ${sbindir}/lvmdump \
"
# Specified explicitly for the udev rules, just in case that it does not get picked
# up automatically:
FILES_${PN}-udevrules = "${nonarch_base_libdir}/udev/rules.d"
RDEPENDS_${PN}-udevrules = "libdevmapper"
RDEPENDS_${PN}_append_class-target = " libdevmapper"
RDEPENDS_${PN}_append_class-nativesdk = " libdevmapper"

RDEPENDS_${PN}-scripts = "${PN} (= ${EXTENDPKGV}) \
                          bash \
                          util-linux \
                          coreutils \
"

# FIXME: ERROR: lvm2-2.03.14-r0 do_package_qa: QA Issue: /usr/sbin/lvm_import_vdo \
#  contained in package lvm2 requires /bin/bash, but no providers found in RDEPENDS_lvm2? [file-rdeps]
RDEPENDS_${PN} = "bash"

RRECOMMENDS_${PN}_class-target = "${PN}-scripts (= ${EXTENDPKGV})"

CONFFILES_${PN} += "${sysconfdir}/lvm/lvm.conf"

do_install_append() {
    rm -f ${D}${libdir}/libdevmapper.so* \
       ${D}${libdir}/libdevmapper.a \
       ${D}${sbindir}/dmsetup* \
       ${D}${sbindir}/dmstats* \
       ${D}${includedir}/libdevmapper.h \
       ${D}${libdir}/pkgconfig/devmapper.pc
}

BBCLASSEXTEND = "native nativesdk"
