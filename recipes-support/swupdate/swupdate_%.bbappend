FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS += "librsync"

#SRC_URI = "file://defconfig"

SRCREV = "${AUTOREV}"
