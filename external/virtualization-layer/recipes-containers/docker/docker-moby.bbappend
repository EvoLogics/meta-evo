FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:mio-2363 = "file://dockerd.sysconfig"

do_install:append:tegra194-evo(){
  install -d ${D}${sysconfdir}/sysconfig
  install -m 0644 ${WORKDIR}/dockerd.sysconfig ${D}${sysconfdir}/sysconfig/
}
