SUMMARY = "Environment initialization scripts"
DESCRIPTION = "These scripts help to initialize the system on the first run"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/tx6:"

PR = "r1"

SRC_URI_mx6-evobb = "file://init \
  file://se \
  file://*-*.sh \
  "

INITSCRIPT_NAME = "evo-envinit"
INITSCRIPT_PARAMS = "defaults 07"

inherit update-rc.d

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
