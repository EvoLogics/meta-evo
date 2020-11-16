FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}/commod-mx6ul:"

DEPENDS += "librsync"

SRC_URI_append_mx6ul-comm-module = "			\
				file://defconfig   				\
				file://swupdate.service     	\
				file://hwrevision				\
				"


SRCREV = "${AUTOREV}"

SYSTEMD_SERVICE_${PN}_mx6-comm-module = "swupdate.service"

do_install_prepend_mx6ul-comm-module(){

	cp tools/swupdate-client_unstripped tools/client_unstripped
	cp tools/swupdate-progress_unstripped tools/progress_unstripped
	cp tools/swupdate-hawkbitcfg_unstripped tools/hawkbitcfg_unstripped
	cp tools/swupdate-sendtohawkbit_unstripped tools/sendtohawkbit_unstripped
}


do_install_append_mx6ul-comm-module(){
	rm -f {D}${systemd_unitdir}/system/swupdate-usb@.service
	rm -f {D}${systemd_unitdir}/system/swupdate-progress.service

	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}

	if [ -n "${HW_REVISION}" ]
    then
        sed -i -e 's!comm-mod 1.0!comm-mod ${HW_REVISION}\/24!g' $${D}/${sysconfdir}/hwrevision
    fi
}
