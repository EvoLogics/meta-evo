# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Roadrunner EvoMini2 platform helpers"
DESCRIPTION = "EvoLogics WiSE init system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

PR = "r1"

SRC_URI = "file://evo-hw \
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

    ## /bin/ip needed for sersh
    #set -x
    #ln -frs ${D}${base_sbindir}/ip ${D}${base_bindir}/ip
}

