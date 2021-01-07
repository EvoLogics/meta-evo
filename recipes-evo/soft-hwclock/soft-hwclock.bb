SUMMARY = "Software Clock to save time when RTC battery is dead"
DESCRIPTION = "This scripts saves the time periodically in a file and loads this file on boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_append:= ":${THISDIR}/${PN}"

FILESEXTRAPATHS_append_mx6ul-comm-module := ":${THISDIR}/${PN}/commod-mx6ul"

PR = "r1"

SRC_URI_mx6ul-comm-module_append = "      \
    file://soft-hwclock                   \
    file://soft-hwclock.service           \
    file://soft-hwclock-tick.service      \
    file://soft-hwclock-tick.timer        \
"
prefix = "/opt/soft-hwclock"

inherit systemd

S = "${WORKDIR}"

FILES_${PN} += "${prefix}/soft-hwclock"
FILES_${PN} += "${prefix}/data"

do_configure() {
	:
}

do_compile() {
	:
}

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module += "      \
   	soft-hwclock.service                          \
    soft-hwclock-tick.service                     \
"

do_install_mx6ul-comm-module(){
	install -d ${D}${prefix}/
  install -d ${D}${prefix}/data
  install -m 0755 ${WORKDIR}/soft-hwclock ${D}${prefix}/

  install -d ${D}${systemd_system_unitdir}/
	install -m 0644 ${WORKDIR}/soft-hwclock.service ${D}${systemd_system_unitdir}/
	install -m 0644 ${WORKDIR}/soft-hwclock-tick.service ${D}${systemd_system_unitdir}/
  install -m 0644 ${WORKDIR}/soft-hwclock-tick.timer ${D}${systemd_system_unitdir}/
}
