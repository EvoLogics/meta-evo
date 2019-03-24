# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "EvoLogics WiSE init system"
DESCRIPTION = "EvoLogics WiSE init system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
#SECTION = ""

RDEPENDS_${PN} = "busybox expect"

# TODO:
# /usr/local/bin/adc \
# /sbin/fw_setenv \
# /sbin/fw_getenv
# 
# brctl
# lxc
# ppp

#         rmmod iptable_nat
#         rmmod veth
#         rmmod bridge


SRC_URI = "file://rootfs/sbin/init \
           file://rootfs/sbin/initctl.exp \
           file://rootfs/etc/init.d/lxc \
           file://rootfs/usr/local/etc/rc.local \
    "

FILES_${PN} += "/usr/local/www /usr/local/etc/rc.local"

PR = "r1"

S = "${WORKDIR}"

do_configure() {
	:
}

do_compile() {
	:
}

#????
#flag for serial only modems
#[ -x /sbin/eth-power ] && /sbin/eth-power on

do_install() {
	install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/rootfs/sbin/init ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/rootfs/sbin/initctl.exp ${D}${base_sbindir}

    mkdir -p ${D}/usr/local/etc
    # install init script runned at start and delete her self
    install -m 0755 ${WORKDIR}/rootfs/usr/local/etc/rc.local ${D}/usr/local/etc
    mkdir -p ${D}/usr/local/www
           
	cd ${D}${base_sbindir}
    ln -sf issue.base issue
}

#FILES_${PN} = "${base_sbindir}/*"

#FILES_${PN} = "${base_sbindir}/init \
#  ${base_sbindir}/initctl.exp \
#  ${base_sbindir}/evo-gen-mac \
#"
#FILES_${PN} = "/sbin/init \
#  /sbin/initctl.exp \
#  /sbin/evo-gen-mac \
#"

# base_bindir='/bin'
# base_libdir='/lib'
# base_prefix=''
# base_sbindir='/sbin'
# bindir='/usr/bin'
# datadir='/usr/share'
# docdir='/usr/share/doc'
# exec_prefix='/usr'
# includedir='/usr/include'
# infodir='/usr/share/info'
# libdir='/usr/lib'
# libexecdir='/usr/libexec'
# localstatedir='/var'
# mandir='/usr/share/man'
# nonarch_base_libdir='/lib'
# nonarch_libdir='/usr/lib'
# oldincludedir='/usr/include'
# prefix='/usr'
# sbindir='/usr/sbin'
# servicedir='/srv'
# sharedstatedir='/com'
# sysconfdir='/etc'
# systemd_system_unitdir='/lib/systemd/system'
# systemd_unitdir='/lib/systemd'
# systemd_user_unitdir='/usr/lib/systemd/user'     

