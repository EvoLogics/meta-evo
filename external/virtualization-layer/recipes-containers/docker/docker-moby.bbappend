FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:mio-2363 = "file://dockerd.sysconfig"

do_install:append:mio-2363(){
  install -d ${D}${sysconfdir}/sysconfig
  install -m 0644 ${WORKDIR}/dockerd.sysconfig ${D}${sysconfdir}/sysconfig/
}
