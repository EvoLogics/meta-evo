FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6:"

DEPENDS += "librsync json-c e2fsprogs "
DEPENDS_append_tegra194-evo = "systemd"

SRC_URI_append_mx6ul-comm-module = "  \
	file://defconfig   				          \
	file://swupdate.service     	      \
	file://hwrevision				            \
	"

SRC_URI_append_mx6 = " \
	file://hwrevision	\
  file://swupdate.default \
	"

SRC_URI_append_tegra194-evo = "                       \
  file://hwrevision                                   \
  file://archive.cfg                                  \
  file://disable-cfi.cfg                              \
  file://disable-uboot.cfg                            \
  file://hash.cfg                                     \
  file://part-format.cfg                              \
  file://signed-images.cfg                            \
  file://systemd.cfg                                  \
  file://0001-Add-support-for-custom-bootloader.patch \
  file://swupdate-bootloader-interface-cboot.sh       \
  "

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
	install -d ${D}/${sysconfdir}/default
	install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
  install -m 0644 ${WORKDIR}/swupdate.default ${D}/${sysconfdir}/default/swupdate
}

do_install_append_tegra194-evo(){
  install -d ${D}/${sysconfdir}
  install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
  install -d ${D}${sbindir}
  install -m 0755 ${WORKDIR}/swupdate-bootloader-interface-cboot.sh ${D}${sbindir}/swupdate-bootloader-interface
}
