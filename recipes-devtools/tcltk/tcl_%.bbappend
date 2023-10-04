V = "${@'${PV}'.replace('lib','')}"
#ON="${@'${BP}'.replace('lib','')}"
SHRT_VER = "${@oe.utils.trim_version("${PV}", 2)}"

FILES:${PN}-msgs     = "${libdir}/tcl${SHRT_VER}/msgs/*"
FILES:${PN}-encoding = "${libdir}/tcl${SHRT_VER}/encoding/*"

PACKAGES =+ "${PN}-msgs ${PN}-encoding"

tcl_package_preprocess:append() {
    rmdir ${PKGD}${bindir_crossscripts}
}

