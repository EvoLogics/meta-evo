FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGES =+ "${PN}-xxd"

FILES:${PN}-xxd = "${bindir}/xxd.vim"

ALTERNATIVE:${PN} = "vi vim"
ALTERNATIVE:${PN}-xxd = "xxd"
