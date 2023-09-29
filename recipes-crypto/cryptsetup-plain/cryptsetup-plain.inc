SUMMARY = "Tiny cryptsetup app to handle only plain dm-crypt volumes"
DESCRIPTION = "Tiny cryptsetup app to handle only plain dm-crypt volumes. \
    Used to open crypted by dm-setup rootfs from initramfs"

AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SECTION = ""

DEPENDS = "cryptsetup"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://cryptsetup-plain.c \
"

inherit pkgconfig

S = "${WORKDIR}"

do_install () {
    install -d ${D}${base_sbindir}/
    install -o root -g root -m 6755 ${S}/${PN} ${D}${base_sbindir}/
}

