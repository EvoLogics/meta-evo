FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGES =+ "${PN}-xxd"

FILES_${PN}-xxd = "${bindir}/xxd.vim"

ALTERNATIVE_${PN} = "vi vim"
ALTERNATIVE_${PN}-xxd = "xxd"
