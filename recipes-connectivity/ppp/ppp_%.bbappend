FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://options \
            file://ppp-login \
"

FILES_${PN} += "/usr/sbin/ppp-login"

do_install_append() {
	install -d ${D}/etc/ppp
    install -m 0644 -o root -g root ${WORKDIR}/options ${D}/etc/ppp
    install -m 0755 ${WORKDIR}/ppp-login ${D}${sbindir}/
}
