FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTRA_OECONF_append = " --with-sys-screenrc=/etc/screenrc"

SRC_URI += "file://screenrc"

do_install_append() {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/screenrc ${D}${sysconfdir}
}
