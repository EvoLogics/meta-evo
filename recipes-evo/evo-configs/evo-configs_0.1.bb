# Copyright (C) 2021 Maksym Komar <komar@evologics.de>

SUMMARY = "EvoLogics various config files used by host and sandbox"
DESCRIPTION = "EvoLogics various config files used by host and sandbox"
AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS += "xz-native"

SRC_URI = "file://bashrc \
           file://vimrc \
           file://screenrc \
           file://tmux.conf \
"
FILES:${PN} = "${datadir}/${PN}/*.xz"

inherit allarch
ALLOW_EMPTY:${PN}-dbg = "0"
ALLOW_EMPTY:${PN}-dev = "0"

do_install() {
    mkdir -p ${D}${datadir}/evo-configs
    for FILE in bashrc screenrc tmux.conf vimrc; do
        xz -9fk ${WORKDIR}/$FILE
        install -m 0644 -o 0 -g 0 ${WORKDIR}/$FILE.xz ${D}${datadir}/evo-configs/
    done
}
