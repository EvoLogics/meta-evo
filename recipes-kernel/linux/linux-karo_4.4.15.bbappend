FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules on EvoLogics baseboard"

PR .= ".0"

SRC_URI += "\
    file://imx6dl-${MACHINE}-defconfig \
    file://imx6dl-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
"

do_configure_prepend() {
    cp ${WORKDIR}/imx6dl-${MACHINE}-defconfig ${WORKDIR}/defconfig
}

