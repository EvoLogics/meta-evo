DESCRIPTION = "Docker for tegra"

inherit packagegroup

RDEPENDS_${PN} = "                \
    nvidia-docker                 \
    nvidia-container-runtime      \
    cudnn-container-csv           \
    libvisionworks-container-csv  \
    libnvidia-container-tools     \
"

COMPATIBLE_MACHINE = "(tegra)"
