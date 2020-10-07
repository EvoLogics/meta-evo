FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/mx6:"

SRC_URI_append = "                        \
    file://chrony-force.sh             	  \
    "

SRC_URI_append_mx6ul-comm-module = "      \
    file://chrony.conf                 	  \
    file://chronyd.service                \
"

SRC_URI_append_mx6-evobb = "              \
    file://chrony.conf                 	  \
    "

FILES_${PN} += "${base_sbindir}/chrony-force.sh"

do_install_append() {
  install -d ${D}${base_sbindir}
    install -m 0755 ${WORKDIR}/chrony-force.sh ${D}${base_sbindir}/
}

do_install_append_mx6ul-comm-module(){
	install -m 0644 ${WORKDIR}/chronyd.service ${D}${systemd_unitdir}/system/

  if [ -n "${BRIDGE_ADDRESS}" ]
  then
    address=$(echo ${BRIDGE_ADDRESS} | awk -F"." '{print $1"."$2"."$3".0/24"}')
    sed -i -e "s!allow 10.0.0.0/24!allow $address!g" ${D}${sysconfdir}/chrony.conf
  fi
}

