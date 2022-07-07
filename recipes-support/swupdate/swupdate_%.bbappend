FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6:"

DEPENDS += "librsync json-c"

SRC_URI_append_mx6ul-comm-module = "			\
				file://defconfig   				\
				file://swupdate.service     	\
				"

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module = "swupdate.service"

