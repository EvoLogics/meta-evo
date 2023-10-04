FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Toradex Freescale i.MX based modules"

SRC_URI = " \
    git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH} \
    file://defconfig \
"

# Load USB functions configurable through configfs (CONFIG_USB_CONFIGFS)
KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = "-${TDX_VER_ITEM}"
PV:append = "+git"

SRCREV = "b8e49e74e60c8eb20328e4aac412f5cf338e9d0f"
SRCBRANCH = "toradex_4.14-2.3.x-imx"
SRCREV_use-head-next = "${AUTOREV}"

DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

# defaults
TDX_VER_ITEM ??= "0"
