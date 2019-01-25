SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules"

require recipes-kernel/linux/linux-imx.inc

DEPENDS += "lzop-native bc-native"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-common:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRCBRANCH = "karo-tx6-linux-4.14.y"
LOCALVERSION = "-karo"

# v4.14.84 
SRCREV = "9fc8575802d3447a639293dbad4c5ad5acc7f9dc"
KERNEL_SRC = "git://github.com/evologics/linux-karo-tx;protocol=git"

SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH} \
           file://defconfig \
"

SRC_URI += "\
    file://imx6-tx6-evobb.cfg \
    file://imx6-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-common.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-evobb.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lcd.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lvds.dtsi;subdir=${S}/arch/arm/boot/dts \
"

KERNEL_IMAGETYPE="uImage"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*)"

do_configure_append() {
    cat ${WORKDIR}/*.cfg >> ${B}/.config
}
