FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/mx6ul-comm-module:"

SRC_URI:append = "${@bb.utils.contains('DISTRO_FEATURES','systemd','file://${BPN}.service','',d)}"

SYSTEMD_SERVICE:${PN}:mx6ul-comm-module = "${BPN}.service"

inherit systemd

do_install:append(){

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -d ${D}${systemd_unitdir}/system/
		install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_unitdir}/system/${BPN}.service
	fi
}
