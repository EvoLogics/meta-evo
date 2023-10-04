DESCRIPTION = "Useful tools for Jetson"

inherit packagegroup

RDEPENDS:${PN} = "               \
    tegra-nvpmodel               \
    tegra-tools                  \
    tegra-redundant-boot         \
    tegra-nv-boot-control-config \
    tegra-boot-tools             \
    tegra-boot-tools-updater	 \
    tegra-sysinstall-tools       \
    tegra-fuse-tool              \
    tegra-eeprom-tool            \
"

COMPATIBLE_MACHINE = "tegra194-evo"
