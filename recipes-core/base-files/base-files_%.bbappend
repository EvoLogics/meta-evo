FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_tx6 := "${THISDIR}/${PN}/tx6:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}/commod-mx6ul:"

#PRINC := "${@int(PRINC) + 1}"
PR .= ".2"
SRC_URI += "file://issue*        \
           "

SRC_URI_mx6ul-comm-module += "  \
    file://print_issue.sh       \
    file://InitGPIO.sh          \
    file://init-gsm             \
"

do_install_append_sama5d2-roadrunner-evo() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.base ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.wise ${D}${sysconfdir}

    cat ${D}/${sysconfdir}/issue >> ${D}${sysconfdir}/issue.base
    cat ${D}/${sysconfdir}/issue >> ${D}${sysconfdir}/issue.wise
    rm -f ${D}/${sysconfdir}/issue.net

	cd ${D}${sysconfdir}
    ln -sf issue.base issue

    cd ${D}${localstatedir}
    # need for toolchain target opkg
    mkdir -p volatile/lock volatile/run
    ln -sf volatile/lock
    ln -sf volatile/run
    ln -sf volatile/log

    cd ${D}
    [ -d tmp ] && rmdir tmp
    ln -sf ${localstatedir}/volatile/tmp

    # create mount points for evologics 'safe etc' setup
    mkdir -p ${D}/usr/local/etc/vol1 ${D}/usr/local/etc/vol2

    # create non-volatile rw partition mount point
    mkdir -p ${D}/data
}

do_install_append_tx6() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.tx6 ${D}${sysconfdir}

	cd ${D}${sysconfdir}
    cat issue.tx6 issue | tee issue
    rm -f issue.net issue.tx6
}


do_install_append_mx6ul-comm-module() {
    install -m 0755 ${WORKDIR}/print_issue.sh ${D}${sysconfdir}/profile.d/print_issue.sh
    install -d ${D}/mnt/storage
    install -m 0777 ${WORKDIR}/remount-rootfs ${D}/bin/remount-rootfs
    install -m 0777 ${WORKDIR}/InitGPIO.sh ${D}/usr/bin/InitGPIO.sh
    install -m 0777 ${WORKDIR}/init-gsm ${D}/bin/init-gsm
}

do_install_basefilesissue_append_mx6ul-comm-module() {
   if [ -n "${DISTRO_NAME}" ]; then
       sed -i 's/%h//g' ${D}${sysconfdir}/issue.net
   fi
}


