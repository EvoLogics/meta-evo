FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/mx6ul-comm-module:"
FILESEXTRAPATHS_prepend_mx6-evobb := "${THISDIR}/${PN}/tegra194-evo:"

#PRINC := "${@int(PRINC) + 1}"
PR .= ".2"
SRC_URI += "file://issue*        \
           "

SRC_URI_append_mx6ul-comm-module = "    \
    file://print_issue.sh               \
    file://share/dot.bashrc             \
    file://share/dot.profile            \
"

SRC_URI_append_tegra194-evo      = "    \
    file://print_issue.sh               \
    file://share/dot.bashrc             \
    file://share/dot.profile            \
"

dirs755_append = " ${sysconfdir}/profile.d"

do_install_append_sama5d2-roadrunner-evo() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.base ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/issue.wise ${D}${sysconfdir}

    if [ -n "${IMAGE_VERSION}" ]; then
        printf "${DISTRO_NAME} "                      > ${D}${sysconfdir}/issue
        printf "${DISTRO_VERSION}-${IMAGE_VERSION} " >> ${D}${sysconfdir}/issue
        printf "\\\n \\\l\n"                         >> ${D}${sysconfdir}/issue
    fi
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

    # make interactive command immediate write to ~/.bash_history
    echo 'export PROMPT_COMMAND="history -a;$PROMPT_COMMAND"' >> ${D}/${sysconfdir}/profile

    # do not put duplicate command to history file and ignore command begin with space
    echo 'export HISTCONTROL=ignoreboth' >> ${D}/${sysconfdir}/profile

    echo 'export LD_LIBRARY_PATH=/usr/local/lib:/usr/lib:/lib' >> ${D}/${sysconfdir}/profile
    echo 'export RLWRAP_HOME=/data' >> ${D}/${sysconfdir}/profile
    echo "alias sudo=''" >> ${D}/${sysconfdir}/profile
    echo '[ -e $HOME/.bashrc ] && . $HOME/.bashrc' >> ${D}/${sysconfdir}/profile
    echo 'export RLWRAP_HOME=/data' >> ${D}/${sysconfdir}/profile
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
SYSROOT_DIRS_append_tegra194-evo = "${sysconfdir}/skel"

do_install_append_mx6ul-comm-module() {
    install -m 0755 ${WORKDIR}/print_issue.sh ${D}${sysconfdir}/profile.d/print_issue.sh
    install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
    install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
    install -d ${D}/mnt/storage
}


do_install_append_tegra194-evo() {
    install -m 0755 ${WORKDIR}/print_issue.sh ${D}${sysconfdir}/profile.d/print_issue.sh
    install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
    install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
    install -d ${D}/mnt/storage
}

