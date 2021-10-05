FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6:"

DEPENDS += "librsync json-c"

SRC_URI_append_mx6ul-comm-module = "			\
				file://defconfig   				\
				file://swupdate.service     	\
				file://hwrevision				\
				"

SRC_URI_append_mx6 = " \
	file://hwrevision	\
	"

SRCREV = "${AUTOREV}"

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module = "swupdate.service"

do_install_append_mx6ul-comm-module(){
	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}

	if [ -n "${HW_REVISION}" ]
	then
		sed -i -e 's!comm-mod 1.0!comm-mod ${HW_REVISION}!g' ${D}/${sysconfdir}/hwrevision
	fi
}

do_install_append_mx6(){
	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
}
