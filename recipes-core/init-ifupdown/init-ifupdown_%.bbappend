FILESEXTRAPATHS:prepend:tx6 := "${THISDIR}/${PN}/tx6:"
FILESEXTRAPATHS:prepend:mio-2363 := "${THISDIR}/${PN}/mio-2363:"

SRC_URI:append:mio-2363 = " \
  file://br0-up.sh \
  file://br0-down.sh \
  "

do_install:append:mio-2363() {
  # dir created in bb already
  install -m 755 ${WORKDIR}/br0-up.sh ${D}${sysconfdir}/network/if-up.d/
  install -m 755 ${WORKDIR}/br0-down.sh ${D}${sysconfdir}/network/if-down.d/
}
