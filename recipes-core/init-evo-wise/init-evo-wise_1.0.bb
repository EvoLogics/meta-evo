# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "EvoLogics WiSE init system"
DESCRIPTION = "EvoLogics WiSE init system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
#SECTION = ""

RDEPENDS_${PN} = "busybox expect lxc-login-sh liblxc evo-configs ppp"
# need satisfy QA for ssh-proxy-sh, but actually don't need it
RDEPENDS_${PN} += "bash"

DEPENDS_${PN} += "xz-native"

ALLOW_EMPTY_${PN}-dbg = "0"
ALLOW_EMPTY_${PN}-dev = "0"

SRC_URI = "file://rootfs/sbin/init \
           file://rootfs/sbin/initctl.exp \
           file://rootfs/sbin/eush \
           file://rootfs/sbin/evo-sandbox \
           file://rootfs/usr/bin/xauth \
           file://rootfs/etc/init.d/lxc \
           file://rootfs/usr/local/etc/rc.local \
           file://rootfs/usr/local/etc/rc-post.d/99-hwtype_autoconfig.sh \
           file://rootfs/etc/udhcpd.conf \
           \
           file://rootfs/opt/lxc/sandbox/config-sd \
           file://rootfs/opt/lxc/sandbox/lxc-dev-populate.sh \
           \
           file://rootfs/opt/lxc/sandbox/init-files/firewall \
           file://rootfs/opt/lxc/sandbox/init-files/lighttpd-evo-proxy.conf \
           file://rootfs/opt/lxc/sandbox/init-files/index.html \
           file://rootfs/opt/lxc/sandbox/init-files/hello.cgi \
           file://rootfs/opt/lxc/sandbox/init-files/eth-dhcp.cgi \
           file://rootfs/opt/lxc/sandbox/init-files/eth-ctl \
           file://rootfs/opt/lxc/sandbox/init-files/www-data.sudoers \
           file://rootfs/opt/lxc/sandbox/init-files/ssh-proxy-sh \
           \
           file://rootfs/opt/lxc/sandbox/init-files/serial2tcp-socat \
           file://rootfs/opt/lxc/sandbox/init-files/serial2tcp-socat.init \
           \
           file://rootfs/opt/lxc/sandbox/init-files/ip-up \
           file://rootfs/opt/lxc/sandbox/init-files/options \
           \
           file://rootfs/opt/lxc/sandbox/init-files/evo.sudoers \
    "

FILES_${PN} += "/usr/local/www \
                /usr/local/etc/rc.local \
                /usr/local/etc/rc-post.d/99-hwtype_autoconfig.sh \
                /opt/lxc/sandbox/ \
"

PR = "r2"

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
    install -m 0755 ${WORKDIR}/rootfs/sbin/init        ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/rootfs/sbin/initctl.exp ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/rootfs/sbin/evo-sandbox ${D}${base_sbindir}

	install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/rootfs/etc/udhcpd.conf ${D}${sysconfdir}

	install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/rootfs/usr/bin/xauth ${D}/usr/bin

    install -m 0755 ${WORKDIR}/rootfs/sbin/eush ${D}${base_sbindir}

    mkdir -p ${D}/usr/local/etc/rc-post.d
    # install init script runned at start and delete her self
    install -m 0755 ${WORKDIR}/rootfs/usr/local/etc/rc-post.d/99-hwtype_autoconfig.sh ${D}/usr/local/etc/rc-post.d/
    install -m 0755 ${WORKDIR}/rootfs/usr/local/etc/rc.local ${D}/usr/local/etc
    sed -i "s/\(IMAGE_VERSION=\).*/\1${DISTRO_VERSION}-${IMAGE_VERSION}/" ${D}/usr/local/etc/rc.local
    mkdir -p ${D}/usr/local/www
           
	cd ${D}${base_sbindir}
    ln -sf issue.base issue

	install -d ${D}/opt/lxc/sandbox/init-files
    install -m 0644 -o root -g root ${WORKDIR}/rootfs/opt/lxc/sandbox/config-sd                ${D}/opt/lxc/sandbox/
    install -m 0755 -o root -g root ${WORKDIR}/rootfs/opt/lxc/sandbox/lxc-dev-populate.sh      ${D}/opt/lxc/sandbox/

    install -m 0755 -o root -g root \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/firewall \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/hello.cgi \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/eth-dhcp.cgi \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/eth-ctl \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/ssh-proxy-sh \
            ${D}/opt/lxc/sandbox/init-files/

    install -m 0644 -o root -g root \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/index.html \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/lighttpd-evo-proxy.conf \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/www-data.sudoers \
        ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/evo.sudoers \
            ${D}/opt/lxc/sandbox/init-files/

    install -m 0755 -o root -g root ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/serial2tcp-socat \
                                    ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/serial2tcp-socat.init \
                                        ${D}/opt/lxc/sandbox/init-files/

    install -m 0755 -o root -g root ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/ip-up    ${D}/opt/lxc/sandbox/init-files/
    install -m 0644 -o root -g root ${WORKDIR}/rootfs/opt/lxc/sandbox/init-files/options  ${D}/opt/lxc/sandbox/init-files/
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

