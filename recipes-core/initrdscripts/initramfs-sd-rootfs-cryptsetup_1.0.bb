SUMMARY = "SD card rootfs with cryptsetup init script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://init-sd-rootfs-cryptsetup.sh"

# NOTE: variable LUKS_PASS_FILE need to be setup in conf/local.conf 

PR = "r2"

S = "${WORKDIR}"

do_install() {
    if [ -z "${LUKS_PASS_FILE}" ]; then
        bberror "Varibables LUKS_PASS_FILE is not set"
        bbfatal_log "Varibables LUKS_PASS_FILE is not set"
    fi
    install -m 0755 ${WORKDIR}/init-sd-rootfs-cryptsetup.sh ${D}/init
    install -m 0600 "${LUKS_PASS_FILE}" ${D}/rootfs.pass
}

inherit allarch

FILES:${PN} += " /init /rootfs.pass"
