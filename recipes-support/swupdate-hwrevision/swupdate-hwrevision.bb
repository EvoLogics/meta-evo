LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"

HW_REVISION ?= "1.0"

FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/mx6:"

FILES_${PN} = "${sysconfdir}/"

SRC_URI_append_mx6 = " \
  file://swupdate.default \
  "

do_install_append_mx6ul-comm-module(){
  echo "comm-mod ${HW_REVISION}" > ${WORKDIR}/hwrevision
	install -d ${D}/${sysconfdir}
	  install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
}

do_install_append_mx6(){
  echo "${MACHINE} ${HW_REVISION}" > ${WORKDIR}/hwrevision
  if [ -n ${EVOHW} ]; then
    sed -i "s/=generic/=${EVOHW}-${EVOSN}/" ${WORKDIR}/swupdate.default
  fi
  install -d ${D}/${sysconfdir}/default
	  install -m 0644 ${WORKDIR}/hwrevision ${D}/${sysconfdir}
    install -m 0644 ${WORKDIR}/swupdate.default ${D}/${sysconfdir}/default/swupdate
}
