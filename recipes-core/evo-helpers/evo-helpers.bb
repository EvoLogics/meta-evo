# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Roadrunner EvoMini2 platform helpers"
DESCRIPTION = "EvoLogics WiSE init system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

PR = "r1"

SRC_URI = "file://evo-hw \
           file://evo-env \
           file://evo-gen-mac \
           file://evo-update \
"

S = "${WORKDIR}"

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/evo-hw ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/evo-env ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/evo-gen-mac ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/evo-update ${D}${base_sbindir}

    ln -srf ${D}${base_sbindir}/evo-env ${D}${base_sbindir}/fw_printenv
    ln -srf ${D}${base_sbindir}/evo-env ${D}${base_sbindir}/fw_setenv
    ln -srf ${D}${base_sbindir}/evo-env ${D}${base_sbindir}/vidt
}

