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
    ${@bb.utils.contains("IMAGE_CONFIGS", "can", "file://can0.service", "", d)} \
"

SYSTEMD_SERVICE_${PN}_mx6ul-comm-module = "${@bb.utils.contains("IMAGE_CONFIGS", "can", "can0.service", "", d)}"

do_install_mx6ul-comm-module(){
    install -d ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth1.network  ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.netdev ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-wwan0.network ${D}${systemd_unitdir}/network/

    if [ -n "${BRIDGE_ADDRESS}" ]
    then
        sed -i -e 's!Address=10.0.0.2/24!Address=${BRIDGE_ADDRESS}\/24!g' ${D}${systemd_unitdir}/network/Bridge.network
    fi

    if [ -n "${BRIDGE_GATEWAY}" ]
    then
        sed -i -e 's!Gateway=10.0.0.1!Gateway=${BRIDGE_GATEWAY}!g' ${D}${systemd_unitdir}/network/Bridge.network
    fi

    install -d ${D}${systemd_system_unitdir}/
    for file in $(find ${WORKDIR} -maxdepth 1 -type f -name *.service); do
        install -m 0644 "$file" ${D}${systemd_system_unitdir}/
    done
}
