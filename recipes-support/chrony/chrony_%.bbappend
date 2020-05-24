FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"


SRC_URI_append_mx6ul-comm-module = "      \
    file://chrony.conf                 	  \
    file://chronyd.service                \
"

do_install_append_mx6ul-comm-module(){
	install -m 0644 ${WORKDIR}/chronyd.service ${D}${systemd_unitdir}/system/

  if [ -n "${BRIDGE_ADDRESS}" ]
  then
    address=$(echo ${BRIDGE_ADDRESS} | awk -F"." '{print $1"."$2"."$3".0/24"}')
    sed -i -e "s!allow 10.0.0.0/24!allow $address!g" ${D}${sysconfdir}/chrony.conf
  fi
}

