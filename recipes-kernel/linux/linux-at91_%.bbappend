FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

SRC_URI += "\
    file://${MACHINE}.dts \
    file://${MACHINE}_defconfig \
"
do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}.dts ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}_defconfig ${WORKDIR}/defconfig
}

