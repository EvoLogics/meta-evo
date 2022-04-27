SUMMARY = "Open crypted by dm-setup rootfs from initramfs"
DESCRIPTION = "Open crypted by dm-setup rootfs from initramfs"
AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SECTION = ""

DEPENDS = "cryptsetup"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://crypt-open-plain-initramfs.c \
"

inherit pkgconfig

S = "${WORKDIR}"

do_compile () {
    set -x
    ## lib libdevmapper dynamically. then libdevmapper depend from musl.so (+500K)
    #${CC} ${LDFLAGS} -o crypt-open-plain-initramfs crypt-open-plain-initramfs.c \
    #     -Wl,-Bstatic -lcryptsetup -lpopt -ljson-c -luuid -Wl,-Bdynamic -ldevmapper

    ${CC} -o crypt-open-plain-initramfs crypt-open-plain-initramfs.c \
         -static -lcryptsetup -lpopt -ljson-c -luuid -ldevmapper 
}

do_install () {
    install -d ${D}${base_sbindir}/
    install -o root -g root -m 6755 ${S}/crypt-open-plain-initramfs ${D}${base_sbindir}/
}

