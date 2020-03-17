FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

DEPENDS += "ncurses-native"

# to fix nor flash
SRCREV = "7e44ddb093c3b8d723e16212c9a04cd56c77d165"

# NOTE: when you add new dts, do not forget add to meta-evo/conf/machine/sama5d2-roadrunner-evo.conf
SRC_URI += "\
    file://adc-remove-regulator-hack.patch \
    file://adc-force-set-csnaat.patch \
    file://spi-nor-prepare-for-romboot-on-shutdown.patch \
    file://add-rstc-reset-gpio.patch \
    \
    file://${MACHINE}.dtsi \
    file://${MACHINE}-eth.dtsi \
    file://${MACHINE}-rs232.dtsi \
    \
    file://${MACHINE}-eth.dts \
    file://${MACHINE}-rs232.dts \
    file://${MACHINE}-rs422.dts \
    file://${MACHINE}-rs485.dts \
    file://${MACHINE}-can.dts \
    \
    file://${MACHINE}-r2.dtsi \
    file://${MACHINE}-r2-eth.dts \
    file://${MACHINE}-r2-rs232.dts \
    \
    file://${MACHINE}_defconfig \
"

# boot time 5.8s
#SRC_URI += "file://debug-memory.cfg"
# boot time 7.3s
#SRC_URI += "file://debug-memory-more.cfg"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}*.dtsi ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}*.dts  ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/${MACHINE}_defconfig ${WORKDIR}/defconfig
}

do_configure_append() {
    CFG="$(ls ${WORKDIR}/*.cfg 2> /dev/null || true)"
    test -n "$CFG" && cat ${WORKDIR}/*.cfg >> ${B}/.config
    return 0
}
