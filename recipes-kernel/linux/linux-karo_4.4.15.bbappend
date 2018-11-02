FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules on EvoLogics baseboard"

PR .= ".0"

SRC_URI += "\
    file://defconfig-imx6qdl-evobb \
    file://imx6-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-evobb.dtsi;subdir=${S}/arch/arm/boot/dts \
"

do_configure_prepend() {
    cp ${WORKDIR}/defconfig-imx6qdl-evobb ${WORKDIR}/defconfig
}

