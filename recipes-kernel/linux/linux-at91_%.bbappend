FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

SRC_URI += "\
    file://${MACHINE}.dts \
    file://${MACHINE}_defconfig \
    file://0001-ACME-patch-to-remove-ADC.patch \
    file://0002-ACME-add-spidev-compatible.patch \
    file://0003-add-spi-nor-mt25q02g.patch \
"
do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}.dts ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}_defconfig ${WORKDIR}/defconfig
}

