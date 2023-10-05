FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:tegra194-evo = "file://docker.service"

inherit systemd

SYSTEMD_SERVICE:${PN}:tegra194-evo += "docker.service"

do_install:append:tegra194-evo(){
  install -d ${D}${systemd_system_unitdir}/
  install -m 0644 ${WORKDIR}/docker.service ${D}${systemd_system_unitdir}/
}