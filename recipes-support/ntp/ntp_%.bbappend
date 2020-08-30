FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_sonobot-payload := "${THISDIR}/${PN}/sonobot-payload:"

SRC_URI_append = " \
  file://ntp-force.sh \
  "

FILES_${PN} += "${base_sbindir}/ntp-force.sh"

do_install_append() {
  install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/ntp-force.sh ${D}${base_sbindir}/
}

