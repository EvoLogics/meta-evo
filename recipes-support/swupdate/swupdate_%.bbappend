FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6:"

DEPENDS += "librsync json-c"

SRC_URI_append_mx6ul-comm-module = "			\
				file://defconfig   				\
				file://swupdate.service     	\
				"

SRC_URI_append_mx6 = " \
  file://swupdate.default \
	"

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module = "swupdate.service"

do_install_append_mx6(){
	install -d ${D}/${sysconfdir}/default
  install -m 0644 ${WORKDIR}/swupdate.default ${D}/${sysconfdir}/default/swupdate
}
