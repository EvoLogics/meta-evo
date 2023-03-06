DESCRIPTION = "Cuda tools and libraries for tegra"

inherit packagegroup

RDEPENDS_${PN} = "          	\
    cuda-nvrtc                \
    cuda-nvgraph              \
    cuda-cudart               \
    cuda-compiler             \
    cuda-libraries            \
    cuda-nvml                 \
    cuda-cuobjdump            \
    cuda-cupti                \
    cuda-gdb                  \
    cuda-memcheck             \
    cuda-nvcc-headers         \
    cuda-nvcc                 \
    cuda-nvdisasm             \
    cuda-nvprof               \
    cuda-nvprune              \
    cuda-nvtx                 \
    cuda-libraries            \
    cuda-driver               \
    cuda-toolkit              \
    cudnn                     \
    cuda-command-line-tools   \
    libcublas                 \
    tensorrt-core             \
    tensorrt-plugins-prebuilt \
"

COMPATIBLE_MACHINE = "(tegra)"
