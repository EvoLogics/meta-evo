FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
PR .= ".0"
COMPATIBLE_MACHINE := '(sam9x60-curiosity|sam9x60-curiosity-sd|sama5d2-roadrunner-.*)'

SRC_URI += "\
    file://0001-load_kernel-Fix-not-setted-CONFIG_OVERRIDE_CMDLINE-n.patch \
    file://0002-Add-quirk-for-ACME-RoadRunner-Berta-D2-board.patch \
    file://0003-Add-quirk-for-ACME-RoadRunner-EvoLogics-board.patch \
"

AT91BOOTSTRAP_CONFIG_sama5d2-roadrunner-evo = "${AT91BOOTSTRAP_MACHINE}_qspi_linux"
AT91BOOTSTRAP_TARGET ??= "${AT91BOOTSTRAP_CONFIG}_defconfig"
AT91BOOTSTRAP_LOAD_sama5d2-roadrunner-evo = "dataflashboot-linux"
# TODO: find how to set it from config
#CONFIG_OF_OFFSET="0x00010000"

