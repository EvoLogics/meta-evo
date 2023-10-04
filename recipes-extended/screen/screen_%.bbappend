FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

EXTRA_OECONF:append = " --with-sys-screenrc=/etc/screenrc"

SRC_URI += "file://screenrc"

do_install:append() {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/screenrc ${D}${sysconfdir}
}
