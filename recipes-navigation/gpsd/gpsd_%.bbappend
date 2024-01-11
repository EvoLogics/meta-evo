FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/mx6ul-comm-module:"

SRC_URI:append:mx6ul-comm-module = "      \
    file://gpsd.commod                    \
    file://gpsd.service             	    \
    "

do_install:append:mx6ul-comm-module(){
  install -d ${D}${systemd_unitdir}/system/
  install -m 0644 ${WORKDIR}/gpsd.service ${D}${systemd_unitdir}/system/

  install -d ${D}${sysconfdir}/default/
  install -m 0644 ${WORKDIR}/gpsd.commod ${D}/${sysconfdir}/default/
}
