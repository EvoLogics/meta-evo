require busybox.inc

ON="${@'${BP}'.replace('-initramfs','')}"
S = "${WORKDIR}/${ON}"
SRC_URI = "http://www.busybox.net/downloads/busybox-${PV}.tar.bz2;name=tarball \
           file://defconfig \
           file://fail_on_no_media.patch \
           file://recognize_connmand.patch \
           file://busybox-cross-menuconfig.patch \
           file://0001-Use-CC-when-linking-instead-of-LD-and-use-CFLAGS-and.patch \
           file://makefile-libbb-race.patch \
           file://CVE-2018-20679.patch \
	   file://CVE-2019-5747.patch \
"
SRC_URI_append_libc-musl = " file://musl.cfg "

#SRC_URI_append_sama5d2-roadrunner-evo = " \
#    file://sh.cfg \
#"

SRC_URI[tarball.md5sum] = "0a367e19cdfd157e8258d87f893ee516"
SRC_URI[tarball.sha256sum] = "97648636e579462296478e0218e65e4bc1e9cd69089a3b1aeb810bff7621efb7"
