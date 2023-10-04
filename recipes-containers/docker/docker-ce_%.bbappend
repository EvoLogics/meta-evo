FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append_tegra194-evo = "file://docker.service"

inherit systemd

SYSTEMD_SERVICE:${PN}_tegra194-evo += "docker.service"

do_install:append_tegra194-evo(){
  install -d ${D}${systemd_system_unitdir}/
  install -m 0644 ${WORKDIR}/docker.service ${D}${systemd_system_unitdir}/
}