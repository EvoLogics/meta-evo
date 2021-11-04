DESCRIPTION = "Cuda tools and libraries for tegra"

inherit packagegroup

RDEPENDS_${PN} = "          \
    cuda-samples            \
    cudnn                   \
    cuda-command-line-tools \
    cuda-libraries          \
    cuda-driver             \
"

COMPATIBLE_MACHINE = "(tegra)"