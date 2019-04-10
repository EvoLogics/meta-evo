FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#PRINC := "${@int(PRINC) + 1}"
PR .= ".2"
SRC_URI += "file://issue*        \
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
    ln -sf volatile/lock
    ln -sf volatile/log
    ln -sf volatile/run

    cd ${D}
    [ -d tmp ] && rmdir tmp
    ln -sf ${localstatedir}/volatile/tmp

    # create mount points for evologics 'safe etc' setup
    mkdir -p ${D}/usr/local/etc/vol1 ${D}/usr/local/etc/vol2
}
