LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

HW_REVISION ?= "1.0"

FILES_${PN} = "${sysconfdir}/hwrevision"

do_install_append_mx6ul-comm-module(){
  echo "comm-mod ${HW_REVISION}" > ${WORKDIR}/hwrevision
}

do_install_append_mx6(){
  echo "${MACHINE} ${HW_REVISION}" > ${WORKDIR}/hwrevision
}

do_install_append(){
	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
}
