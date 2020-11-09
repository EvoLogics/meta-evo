FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}/commod-mx6ul:"

DEPENDS += "librsync"

#SRC_URI = "file://defconfig"


SRCREV = "${AUTOREV}"


do_install_prepend_mx6ul-comm-module(){

	cp tools/swupdate-client_unstripped tools/client_unstripped
	cp tools/swupdate-progress_unstripped tools/progress_unstripped
	cp tools/swupdate-hawkbitcfg_unstripped tools/hawkbitcfg_unstripped
	cp tools/swupdate-sendtohawkbit_unstripped tools/sendtohawkbit_unstripped
}
