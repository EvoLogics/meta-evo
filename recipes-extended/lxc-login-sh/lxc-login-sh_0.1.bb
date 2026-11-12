DESCRIPTION = "Linux Containers tools console login"
SECTION = "extended"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM="file://${WORKDIR}/lxc-login-sh.c;beginline=4;endline=15;md5=840dbf26bec65cb9092f42bf1242debf"
PR = "r3"

SRC_URI = " \
  file://lxc-login-sh.c\
"

S = "${WORKDIR}"

do_compile () {
    ${CC} ${CFLAGS} ${LDFLAGS} lxc-login-sh.c -o lxc-login-sh
}

do_install () {
    install -d ${D}${bindir}/
    install -o root -g root -m 6755 ${S}/lxc-login-sh ${D}${bindir}/
}

pkg_postinst_${PN} () {
    # if installing package with sendbox enabled, it will make root:root permission
    # which is 10000:10000 id
    chown 0:0 $D${bindir}/lxc-login-sh
    chmod 6755 $D${bindir}/lxc-login-sh
    touch $D${sysconfdir}/shells
    grep -q "usr/bin/lxc-login-sh" $D${sysconfdir}/shells || echo "/usr/bin/lxc-login-sh" >> $D${sysconfdir}/shells
}

pkg_postrm_${PN} () {
    if [ -e $D${sysconfdir}/shells ]; then
        # for some reason busybox sed in postrm was give an error "sed: unsupported command \"
        # if /\/usr/\/... was used
        sed -i -e '\,usr/bin/lxc-login-sh,d' $D${sysconfdir}/shells
    fi
}

# sources/openembedded-core/meta-skeleton/recipes-skeleton/useradd/useradd-example.bb

