FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend:mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"
FILESEXTRAPATHS:prepend:mx6-evobb := "${THISDIR}/mx6:"
FILESEXTRAPATHS:prepend:tegra194-evo := "${THISDIR}/tegra194-evo:"

SRC_URI:append = "                        \
    file://chrony.conf                    \
    file://chrony-force.sh             	  \
    "

SRC_URI:append:mx6ul-comm-module = "      \
    file://chrony.conf                 	  \
    file://chronyd.service                \
"

SRC_URI:append:mx6-evobb = "              \
    file://chrony.conf                 	  \
    "
SRC_URI:append:tegra194-evo = "           \
    file://chrony.conf                    \
    file://chronyd.service                \
    "

FILES:${PN} += "${base_sbindir}/chrony-force.sh"

do_configure:append() {
    # getrandom() slowdown bootup run of chrony to 2 min.
    # Force to use embedded UTI_GetRandomBytesUrandom() which use /dev/urandom
    sed -i '/HAVE_GETRANDOM/d' config.h
}

do_install:append() {
  install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/chrony-force.sh ${D}${base_sbindir}/
}

do_install:append:mx6ul-comm-module(){
	install -m 0644 ${WORKDIR}/chronyd.service ${D}${systemd_unitdir}/system/

  if [ -n "${BRIDGE_ADDRESS}" ]
  then
    address=$(echo ${BRIDGE_ADDRESS} | awk -F"." '{print $1"."$2"."$3".0/24"}')
    sed -i -e "s!allow 10.0.0.0/24!allow $address!g" ${D}${sysconfdir}/chrony.conf
  fi
}

do_install:append:tegra194-evo(){
  install -m 0644 ${WORKDIR}/chronyd.service ${D}${systemd_unitdir}/system/
}

