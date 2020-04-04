SUMMARY = "Machine specific systemd units"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r23"
inherit systemd

NATIVE_SYSTEMD_SUPPORT = "1"
ALLOW_EMPTY_${PN} = "1"

FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"

# Don't generate empty -dbg package
PACKAGES = "${PN}"

SRC_URI_mx6ul-comm-module += "  \
    file://10-eth0.network      \
    file://10-eth1.network      \
    file://Bridge.network       \
    file://Bridge.netdev        \
    file://10-wwan0.network     \
    file://can0.service         \
"


SYSTEMD_SERVICE_${PN}_mx6ul-comm-module += "    \
    can0.service                                \
"

do_install_mx6ul-comm-module(){
    install -d ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth1.network  ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.netdev ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-wwan0.network ${D}${systemd_unitdir}/network/

    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/can0.service ${D}${systemd_system_unitdir}/
}
