FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Additional configure options for pure-ftpd

EXTRA_OECONF:append = " --with-puredb --with-ftpwho"

SRC_URI:append:mx6-evobb = "         \
  file://${PN}.init                  \
  file://${PN}.monitd                \
  "
SRC_URI:append:mx6ul-comm-module = " \
  file://${PN}.default               \
  file://${PN}.service               \
"

SYSTEMD_SERVICE:${PN}_mx6ul-comm-module = "${PN}.service"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 80"

inherit update-rc.d systemd

do_install:append() {

  if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
    install -d ${D}${sysconfdir}/default
    install -m 0755 ${WORKDIR}/${PN}.default ${D}${sysconfdir}/default/${PN}
    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/${PN}.service ${D}${systemd_system_unitdir}/
  else
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/${PN}.init ${D}${sysconfdir}/init.d/${PN}
    install -d ${D}${sysconfdir}/monit.d
    install -m 0755 ${WORKDIR}/${PN}.monitd ${D}${sysconfdir}/monit.d/${PN}
  fi
}

