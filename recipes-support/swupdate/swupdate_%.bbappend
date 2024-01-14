FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS:prepend:mx6-evobb := "${THISDIR}/${PN}/mx6:"

DEPENDS += "librsync json-c e2fsprogs "
DEPENDS:append:tegra194-evo = "systemd"

SRC_URI:append:mx6ul-comm-module = "			 \
				file://defconfig   				         \
				file://swupdate.service     	     \
				"

SRC_URI:append:tegra194-evo = "                       \
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

SYSTEMD_SERVICE:${PN}:mx6ul-comm-module = "swupdate.service"


do_install:append:tegra194-evo(){
  install -d ${D}/${sysconfdir}
  install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
  install -d ${D}${sbindir}
  install -m 0755 ${WORKDIR}/swupdate-bootloader-interface-cboot.sh ${D}${sbindir}/swupdate-bootloader-interface
}
