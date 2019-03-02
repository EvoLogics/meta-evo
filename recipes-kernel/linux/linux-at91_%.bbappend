FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

DEPENDS += "ncurses-native"

# to fix nor flash
SRCREV = "7e44ddb093c3b8d723e16212c9a04cd56c77d165"

SRC_URI += "\
    file://adc-remove-regulator-hack.patch \
    file://adc-force-set-ncpha.patch \
    file://${MACHINE}.dtsi \
    file://${MACHINE}-eth.dts \
    file://${MACHINE}-rs232.dts \
    file://${MACHINE}-rs422.dts \
    file://${MACHINE}-rs485.dts \
    file://${MACHINE}-can.dts \
    file://${MACHINE}_defconfig \
"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}.dtsi ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}-*.dts ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}_defconfig ${WORKDIR}/defconfig
}

