DESCRIPTION = "Useful tools for Jetson"

inherit packagegroup

RDEPENDS_${PN} = "               \
    tegra-nvpmodel               \
    tegra-tools                  \
    tegra-redundant-boot         \
    tegra-nv-boot-control-config \
    tegra-boot-tools             \
"

COMPATIBLE_MACHINE = "(tegra)"
