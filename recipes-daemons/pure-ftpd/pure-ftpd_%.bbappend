FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Additional configure options for pure-ftpd
EXTRA_OECONF_append = " --with-puredb --with-ftpwho "

SRC_URI_append = " \
  file://${PN}.init \
  file://${PN}.monitd \
  "

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 80"

inherit update-rc.d

do_install_append() {
  install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/${PN}.init ${D}${sysconfdir}/init.d/${PN}
  install -d ${D}${sysconfdir}/monit.d
    install -m 0755 ${WORKDIR}/${PN}.monitd ${D}${sysconfdir}/monit.d/${PN}
}

