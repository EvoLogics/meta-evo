SUMMARY = "Environment initialization scripts"
DESCRIPTION = "These scripts help to initialize the system on the first run"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}/commod-mx6ul:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/tx6:"

PR = "r1"

SRC_URI_mx6-evobb = "file://init \
  file://se \
  file://*-*.sh \
  "

SRC_URI_mx6ul-comm-module = "  						\
				file://initgpio.sh 					      \
				file://07-sshd-dropbear-fix.sh		\
				file://08-sshd-dropbear-keys.sh   \
				file://09-monit-id.sh				      \
				file://10-resize-home.sh 			    \
        file://12-mount-storage.sh        \
				file://20-cp-from-skel.sh 			  \
        file://21-generate-mac.sh         \
				file://30-add-dune-cfg.sh 			  \
				file://31-create-dune-dirs.sh 		\
        file://32-create-soft-hwclock-dirs.sh     \
				file://systemd-firstboot.sh 		  \
				file://se 							          \
        file://init-gpio.service			    \
        file://systemd-firstboot.service	\
"

INITSCRIPT_NAME = "evo-envinit"
INITSCRIPT_PARAMS = "defaults 07"

inherit update-rc.d systemd

S = "${WORKDIR}"

do_configure() {
	:
}

do_compile() {
	:
}

do_install_mx6-evobb() {
	install -d ${D}${base_sbindir}/evo-envinit
    install -m 0755 ${WORKDIR}/se ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/*-*.sh ${D}${base_sbindir}/evo-envinit/

  	install -d ${D}${sysconfdir}/init.d/
  	install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/evo-envinit
}

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module += "     \
   	init-gpio.service                            \
   	systemd-firstboot.service                    \
"

do_install_mx6ul-comm-module(){
	install -d ${D}${systemd_system_unitdir}/
	install -m 0644 ${WORKDIR}/init-gpio.service ${D}${systemd_system_unitdir}/
	install -m 0644 ${WORKDIR}/systemd-firstboot.service ${D}${systemd_system_unitdir}/

    install -d ${D}${base_sbindir}/evo-envinit
    install -m 0755 ${WORKDIR}/*-*.sh ${D}${base_sbindir}/evo-envinit/
    install -m 0755 ${WORKDIR}/initgpio.sh ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/systemd-firstboot.sh ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/se ${D}${base_sbindir}/
}
