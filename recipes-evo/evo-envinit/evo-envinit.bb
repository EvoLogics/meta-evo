SUMMARY = "Environment initialization scripts"
DESCRIPTION = "These scripts help to initialize the system on the first run"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6:"

PR = "r1"

SRC_URI_mx6-evobb = " \
  file://init \
  file://se \
  file://abtool \
  file://evohw-config \
  file://06-mount-boot.sh \
  file://07-sshd-dropbear-fix.sh \
  file://08-sshd-dropbear-keys.sh \
  file://09-monit-id.sh \
  file://10-add-eth0-sn.sh \
  file://11-add-kvm-group.sh \
  file://13-format-storage.sh \
  file://15-mount-storage.sh \
  file://16-mk-storage-dirs.sh \
  file://20-cp-from-skel.sh \
  file://31-create-dune-dirs.sh \
  file://32-create-soft-hwclock-dirs.sh \
  file://33-create-sinaps-dirs.sh \
  file://34-unpack-assets.sh \
  file://97-envinit-done.sh \
  file://98-mark-root-good.sh \
  file://99-reboot.sh \
  "

SRC_URI_append_mx6ul-comm-module = "            \
        file://initgpio.sh                      \
        file://update-util                      \
        file://comm-hw                          \
        file://07-sshd-dropbear-fix.sh          \
        file://08-sshd-dropbear-keys.sh         \
        file://09-monit-id.sh                   \
        file://11-create-swupdate-env.sh        \
        file://12-mount-storage.sh              \
        file://20-cp-from-skel.sh               \
        file://21-generate-mac.sh               \
        file://30-add-dune-cfg.sh               \
        file://31-create-dune-dirs.sh           \
        file://32-create-soft-hwclock-dirs.sh   \
        file://33-create-sinaps-dirs.sh         \
        file://40-create-pure-ftpd-db.sh        \
        file://systemd-firstboot.sh             \
        file://se                               \
        file://init-gpio.service                \
        file://systemd-firstboot.service        \
        file://mark-good.service                \
"

INITSCRIPT_NAME = "evo-envinit"
INITSCRIPT_PARAMS = "defaults 07"

inherit update-rc.d systemd

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
    install -m 0755 ${WORKDIR}/abtool ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/evohw-config ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/*-*.sh ${D}${base_sbindir}/evo-envinit/

    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/evo-envinit
}

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module += "     \
    init-gpio.service                            \
    systemd-firstboot.service                    \
    mark-good.service                            \
"

do_install_mx6ul-comm-module(){

    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/init-gpio.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/systemd-firstboot.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/mark-good.service ${D}${systemd_system_unitdir}/

    install -d ${D}${base_sbindir}/evo-envinit
    install -m 0755 ${WORKDIR}/*-*.sh ${D}${base_sbindir}/evo-envinit/
    install -m 0755 ${WORKDIR}/initgpio.sh ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/update-util ${D}${base_sbindir}/update-util
    install -m 0755 ${WORKDIR}/comm-hw ${D}${base_sbindir}/

    install -m 0755 ${WORKDIR}/systemd-firstboot.sh ${D}${base_sbindir}/
    install -m 0755 ${WORKDIR}/se ${D}${base_sbindir}/

    if ${@bb.utils.contains("IMAGE_CONFIGS","enablesw",'true','false',d)}; then
        echo "\n\n\n#setup serial port"                         >> ${D}${base_sbindir}/initgpio.sh
        echo "stty -F /dev/ttymxc6 115200 raw"                  >> ${D}${base_sbindir}/initgpio.sh
        echo "sleep 0.2"                                        >> ${D}${base_sbindir}/initgpio.sh
        echo "printf \"WIFI_SW=1\\\r\\\n\" > /dev/ttymxc6"      >> ${D}${base_sbindir}/initgpio.sh
        echo "sleep 0.2"                                        >> ${D}${base_sbindir}/initgpio.sh
        echo "printf \"GPS_SW=1\\\r\\\n\" > /dev/ttymxc6"       >> ${D}${base_sbindir}/initgpio.sh
        echo "sleep 0.2"                                        >> ${D}${base_sbindir}/initgpio.sh
        echo "printf \"XBEE_SW=1\\\r\\\n\" > /dev/ttymxc6"      >> ${D}${base_sbindir}/initgpio.sh
        echo "sleep 0.2"                                        >> ${D}${base_sbindir}/initgpio.sh
    fi

    if ${@bb.utils.contains("IMAGE_CONFIGS","gpspps",'true','false',d)}; then
        echo "\r\n/sbin/comm-hw pps_sel gps\r\n"                   >> ${D}${base_sbindir}/initgpio.sh
    fi
    if ${@bb.utils.contains("IMAGE_CONFIGS","atmclkpps",'true','false',d)}; then
        echo "\r\n/sbin/comm-hw pps_sel atmclk\r\n"                >> ${D}${base_sbindir}/initgpio.sh
    fi
}
