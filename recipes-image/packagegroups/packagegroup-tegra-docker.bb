DESCRIPTION = "Docker for tegra"

inherit packagegroup

RDEPENDS_${PN} = "                \
    nvidia-docker                 \
    nvidia-container-runtime      \
    nvidia-container-toolkit      \
    cudnn-container-csv           \
    libvisionworks-container-csv  \
    libnvidia-container-tools     \
    packagegroup-cuda             \
"

COMPATIBLE_MACHINE = "tegra194-evo"
