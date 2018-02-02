FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := "(sama5d2-roadrunner.*)"

SRC_URI += "\
    file://${MACHINE}_defconfig \
    file://0001-fix-tupo-with-lost-struct-spi_flash.patch \
    file://0002-bootargs-can-be-not-only-NULL-but-empty-string.patch \
"
# TODO: find how to set it from config
#CONFIG_OF_OFFSET="0x00010000"

do_configure_prepend() {
    cp ${WORKDIR}/${MACHINE}_defconfig ${S}/.config
}

