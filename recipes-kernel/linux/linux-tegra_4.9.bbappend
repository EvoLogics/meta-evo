FILESEXTRAPATHS_prepend_tegra194-evo := "${THISDIR}/linux-tegra:"

SRC_URI_append_tegra194-evo = "  \
    file://Makefile              \
    file://tegra194-evo.dts      \
    file://tegra194-common.dtsi  \
    file://tegra194-base.dtsi    \
"

do_configure_prepend_tegra194-evo() {
  cp ${WORKDIR}/*.dtsi ${S}/nvidia/platform/t19x/jakku/kernel-dts/common
  cp ${WORKDIR}/*.dts ${S}/nvidia/platform/t19x/jakku/kernel-dts
  cp ${WORKDIR}/Makefile ${S}/nvidia/platform/t19x/jakku/kernel-dts
}

COMPATIBLE_MACHINE .= "|tegra194-evo"
