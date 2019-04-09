FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

SRC_URI += "\
    file://${MACHINE}_defconfig \
    file://fix-sdmmc0-pins-to-work-with-qspi-flash.patch \
"

# fixed problem with gcc 8
# https://github.com/linux4sam/at91bootstrap/commit/afbe83496893c100a245d1f414eb4008ab36fd0a
SRCREV = "afbe83496893c100a245d1f414eb4008ab36fd0a"

AT91BOOTSTRAP_LOAD_sama5d2-roadrunner-evo = "dataflashboot-linux"
# TODO: find how to set it from config
#CONFIG_OF_OFFSET="0x00010000"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
}

