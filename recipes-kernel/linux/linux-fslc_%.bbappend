FILESEXTRAPATHS_prepend := "${THISDIR}/linux-karo-common:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SUMMARY = "Linux Kernel for Ka-Ro electronics TX6 Computer-On-Modules on EvoLogics baseboard"

PR .= ".0"

SRCREV_sdma-imx6q = "55edf5202154de59ee1c6a5b6b6ba6fa54571515"
SRC_URI[sdma-imx6q.md5sum] = "03405e6d82f1fe8ae1517d0a294027dd"
SRC_URI[sdma-imx6q.sha256sum] = "7790c161b7e013a9dbcbffb17cc5d4cb63d952949a505647e4679f02d04c4784"

SRC_URI += "\
    file://imx6-tx6-evobb.cfg \
    file://imx6-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-common.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6-tx6-evobb.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lcd.dtsi;subdir=${S}/arch/arm/boot/dts \
    file://imx6qdl-tx6-lvds.dtsi;subdir=${S}/arch/arm/boot/dts \
    https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/imx/sdma/sdma-imx6q.bin?id=${SRCREV_sdma-imx6q};subdir=${S}/firmware/imx/sdma/;downloadfilename=sdma-imx6q.bin;name=sdma-imx6q \
"

KERNEL_IMAGETYPE="uImage"

do_configure_append() {
    cat ${WORKDIR}/*.cfg >> ${B}/.config
}

