DESCRIPTION = "Linux Containers tools console login"
SECTION = "extended"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM="file://${WORKDIR}/lxc-login-sh.c;beginline=4;endline=15;md5=e0e3d4e18ff1a38b0ba46ebb8b05c2b5"
PR = "r2"

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
    touch $D${sysconfdir}/shells
    grep -q "usr/bin/lxc-login-sh" $D${sysconfdir}/shells || echo "/usr/bin/lxc-login-sh" >> $D${sysconfdir}/shells
}

pkg_postrm_${PN} () {
    if [ -e $D${sysconfdir}/shells ]; then
        sed -i -e '/\/usr/\/bin\/lxc-login-sh/d' $D${sysconfdir}/shells
    fi
}

# sources/openembedded-core/meta-skeleton/recipes-skeleton/useradd/useradd-example.bb

