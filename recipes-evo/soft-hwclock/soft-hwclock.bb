SUMMARY = "Software Clock to save time when RTC battery is dead"
DESCRIPTION = "This scripts saves the time periodically in a file and loads this file on boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_append:= ":${THISDIR}/${PN}"

FILESEXTRAPATHS_append_mx6ul-comm-module := ":${THISDIR}/${PN}/commod-mx6ul"

PR = "r1"


SRC_URI = " \
    file://soft-hwclock \
    "

SRC_URI_mx6ul-comm-module_append = "      \
    file://soft-hwclock.service           \
    file://soft-hwclock-tick.service      \
    file://soft-hwclock-tick.timer        \
"

SRC_URI_append_mx6 = " \
    file://soft-hwclock.init \
    file://soft-hwclock.init.monitd \
"

prefix = "/opt/soft-hwclock"

inherit systemd update-rc.d

INITSCRIPT_NAME = "soft-hwclock"
INITSCRIPT_PARAMS = "defaults 30"

S = "${WORKDIR}"

FILES_${PN}_mx6ul-comm-module = "${prefix}/soft-hwclock ${prefix}/data/"
FILES_${PN}_mx6 = "${prefix}/soft-hwclock ${prefix}/data/ ${sysconfdir}/init.d ${sysconfdir}/monit.d/"

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

do_install_mx6(){
	install -d ${D}${prefix}/
    install -m 0755 ${WORKDIR}/soft-hwclock ${D}${prefix}/
  install -d ${D}${prefix}/data

  install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/soft-hwclock.init ${D}${sysconfdir}/init.d/soft-hwclock

  # monit unit
  install -d ${D}${sysconfdir}/monit.d
    install -m 0644 ${WORKDIR}/soft-hwclock.init.monitd ${D}${sysconfdir}/monit.d/soft-hwclock
}
