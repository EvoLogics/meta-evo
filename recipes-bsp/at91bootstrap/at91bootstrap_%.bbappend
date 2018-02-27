FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner-.*)"

SRC_URI += "\
    file://${MACHINE}_defconfig \
"
# TODO: find how to set it from config
#CONFIG_OF_OFFSET="0x00010000"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
}

