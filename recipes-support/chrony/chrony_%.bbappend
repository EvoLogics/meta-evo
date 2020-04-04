FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"


SRC_URI_append_mx6ul-comm-module = "      \
    file://chrony.conf                 	  \ 
    file://chronyd.service                \
"

do_install_append_mx6ul-comm-module(){
	install -m 0644 ${WORKDIR}/chronyd.service ${D}${systemd_unitdir}/system/
}

