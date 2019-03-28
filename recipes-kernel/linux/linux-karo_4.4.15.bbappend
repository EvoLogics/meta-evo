FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-common:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules on EvoLogics baseboard"

PR .= ".0"

SRC_URI += "\
    file://imx6-tx6-evobb.cfg \
    file://0001-fdt-fix-extend-of-cmd-line.patch \
    file://imx6-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-common.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-evobb.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lcd.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lvds.dtsi;subdir=${S}/arch/arm/boot/dts \
"

do_configure_append() {
    cat ${WORKDIR}/*.cfg >> ${B}/.config
}

