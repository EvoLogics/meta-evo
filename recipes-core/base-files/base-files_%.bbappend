FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}/commod-mx6ul:"

#PRINC := "${@int(PRINC) + 1}"
PR .= ".2"
SRC_URI += "file://issue*        \
           "

SRC_URI_append_mx6ul-comm-module = "    \
    file://print_issue.sh               \
    file://share/dot.bashrc             \
    file://share/dot.profile            \
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

##########################
### MX6-EVOBB Specific ###
##########################

FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/tx6:"

do_install_append_mx6-evobb() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.tx6 ${D}${sysconfdir}

	cd ${D}${sysconfdir}
    cat issue.tx6 issue | tee issue
    rm -f issue.net issue.tx6
}

SYSROOT_DIRS_append_mx6ul-comm-module = "${sysconfdir}/skel"

do_install_append_mx6ul-comm-module() {
    install -m 0755 ${WORKDIR}/print_issue.sh ${D}${sysconfdir}/profile.d/print_issue.sh
    install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
    install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
    install -d ${D}/mnt/storage
}