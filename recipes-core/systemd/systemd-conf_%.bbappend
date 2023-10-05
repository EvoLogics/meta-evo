SUMMARY = "Machine specific systemd units"

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR = "r23"
inherit systemd

NATIVE_SYSTEMD_SUPPORT = "1"
ALLOW_EMPTY:${PN} = "1"


# Don't generate empty -dbg package
PACKAGES = "${PN}"

SRC_URI:append:mx6ul-comm-module = "  \
    file://10-eth0.network      \
    file://10-eth1.network      \
    file://Bridge.network       \
    file://Bridge.netdev        \
    file://10-wwan0.network     \
    ${@bb.utils.contains("IMAGE_CONFIGS", "can", "file://can0.service", "", d)} \
"

SRC_URI:append_tegra194-evo = "     \
    file://10-watchdog.conf         \
    file://10-eth0.network          \
    file://90-dhcp-default.network  \
    file://system.conf              \
    ${@bb.utils.contains("IMAGE_CONFIGS", "can", "file://can0.service", "", d)} \
"

SYSTEMD_SERVICE:${PN}_mx6ul-comm-module = "${@bb.utils.contains("IMAGE_CONFIGS", "can", "can0.service", "", d)}"
SYSTEMD_SERVICE:${PN}_tegra194-evo = "${@bb.utils.contains("IMAGE_CONFIGS", "can", "can0.service", "", d)}"


do_install_mx6ul-comm-module(){
    install -d ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-eth1.network  ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.network ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/Bridge.netdev ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/10-wwan0.network ${D}${systemd_unitdir}/network/

    if [ -n "${BRIDGE_ADDRESS}" ]
    then
        sed -i -e 's!Address=10.0.0.2/24!Address=${BRIDGE_ADDRESS}!g' ${D}${systemd_unitdir}/network/Bridge.network
    fi

    if [ -n "${BRIDGE_GATEWAY}" ]
    then
        sed -i -e 's!Gateway=10.0.0.1!Gateway=${BRIDGE_GATEWAY}!g' ${D}${systemd_unitdir}/network/Bridge.network
    fi

    if ${@bb.utils.contains("IMAGE_CONFIGS","streamcaster",'true','false',d)};
    then
      # Disable bridge network
      rm -f ${D}${systemd_unitdir}/network/Bridge.network
      rm -f ${D}${systemd_unitdir}/network/Bridge.netdev
      rm -f ${D}${systemd_unitdir}/network/10-wwan0.network

      sed -i -e 's!Bridge=br0!Address=10.0.0.2/24!g' ${D}${systemd_unitdir}/network/10-eth1.network
      echo "Address=10.0.0.1/24"                  >> ${D}${systemd_unitdir}/network/10-eth1.network

      if ${@bb.utils.contains("IMAGE_CONFIGS","iridium",'true','false',d)};
      then
         echo "Address=10.0.1.2/24"                  >> ${D}${systemd_unitdir}/network/10-eth1.network
         echo "Gateway=10.0.1.1"                     >> ${D}${systemd_unitdir}/network/10-eth1.network
      fi

      if [ -n "${EXTERNAL_IP}" ]
      then
        sed -i -e 's!Bridge=br0!Address=${EXTERNAL_IP}!g' ${D}${systemd_unitdir}/network/10-eth0.network
      else
        sed -i -e 's!Bridge=br0!Address=172.16.222.2/16!g' ${D}${systemd_unitdir}/network/10-eth0.network
      fi

      if [ -n "${EXTERNAL_GATEWAY}" ]
      then
        echo "Gateway=${EXTERNAL_GATEWAY}"             >> ${D}${systemd_unitdir}/network/10-eth0.network
      fi
    fi

    if ${@bb.utils.contains("IMAGE_CONFIGS","EC25",'true','false',d)};
    then
        sed -i -e 's!Bridge=br0!DHCP=ipv4!g' ${D}${systemd_unitdir}/network/10-wwan0.network
        sed -i -e 's!Gateway=10.0.0.1! !g' ${D}${systemd_unitdir}/network/Bridge.network
    fi

    install -d ${D}${systemd_system_unitdir}/
    for file in $(find ${WORKDIR} -maxdepth 1 -type f -name *.service); do
        install -m 0644 "$file" ${D}${systemd_system_unitdir}/
    done
}

do_install:append_tegra194-evo() {
    install -d ${D}${sysconfdir}/systemd/network

    install -m 0644 ../system.conf ${D}${sysconfdir}/systemd/system.conf

    for file in $(find ${WORKDIR} -maxdepth 1 -type f -name *.network); do
        install -m 0644 "$file" ${D}${sysconfdir}/systemd/network/
    done
    install -d ${D}${systemd_system_unitdir}/
    for file in $(find ${WORKDIR} -maxdepth 1 -type f -name *.service); do
        install -m 0644 "$file" ${D}${systemd_system_unitdir}/
    done

    [ -e ${WORKDIR}/10-watchdog.conf ] && \
      install -m 0644 ${WORKDIR}/10-watchdog.conf ${D}${systemd_unitdir}/system.conf.d/10-watchdog.conf

    rm -rf ${D}${systemd_unitdir}/network/wired.network
    rm -rf ${D}${systemd_unitdir}/network/80-wired.network

    if [ -n "${IP_ALIAS}" ]
    then
      echo "\n\n[Address]\nLabel=eth0:evo\nAddress=${IP_ALIAS}" >> ${D}${sysconfdir}/systemd/network/10-eth0.network
      # Do not add route for IP alias
      echo "\nAddPrefixRoute=false\n" >> ${D}${sysconfdir}/systemd/network/10-eth0.network
    fi
}

FILES:${PN} += "\
    ${systemd_system_unitdir} \
    ${sysconfdir} \
"
