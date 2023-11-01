FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Toradex Freescale i.MX based modules"

SRC_URI = " \
    git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH:tdx};name=tdx \
    git://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git;protocol=git;branch=${SRCBRANCH:fw};subpath=imx;destsuffix=git/firmware/imx;name=fw \
    file://defconfig \
    file://0002-net-fec-reset-PHY-whenever-enet-out-clock-has-been-r.patch \
    file://imx6-tx6-evobb.cfg \
    file://imx6-tx6-evobb-iptables.cfg \
    file://imx6-${MACHINE}.dts \
    file://imx6-tx6-common.dtsi \
    file://imx6-tx6-evobb.dtsi \
    file://imx6qdl-tx6.dtsi \
    file://imx6qdl-tx6-lcd.dtsi \
    file://imx6qdl-tx6-lvds.dtsi \
"

# Load USB functions configurable through configfs (CONFIG_USB_CONFIGFS)
KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

KERNEL_IMAGETYPE = "uImage"

# Come on, that won't be updated, nail it
PV = "4.14.170+${SRCBRANCH:tdx}"

SRCREV:tdx = "b8e49e74e60c8eb20328e4aac412f5cf338e9d0f"
SRCBRANCH:tdx = "toradex_4.14-2.3.x-imx"
SRCREV:fw = "55edf5202154de59ee1c6a5b6b6ba6fa54571515"
SRCBRANCH:fw = "main"

DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

PR .= ".0"

do_configure:prepend() {
  cp -f ${WORKDIR}/*.dtsi ${S}/arch/arm/boot/dts/
  cp -f ${WORKDIR}/*.dts ${S}/arch/arm/boot/dts/
}

do_configure:append() {
    CFG="$(ls ${WORKDIR}/*.cfg 2> /dev/null || true)"
    test -n "$CFG" && cat ${WORKDIR}/*.cfg >> ${B}/.config || true
}
