DESCRIPTION = "Cuda tools and libraries for tegra"

inherit packagegroup

RDEPENDS_${PN} = "          	\
    cuda-nvrtc                \
    cuda-nvgraph              \
    cuda-cusolver             \
    cuda-cufft                \
    cuda-curand               \
    cuda-cusparse             \
    cuda-npp                  \
    cuda-cudart               \
    cuda-compiler             \
    cuda-libraries            \
    cuda-nvml                 \
    cuda-cuobjdump            \
    cuda-cupti                \
    cuda-gdb                  \
    cuda-memcheck             \
    cuda-misc-headers         \
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
    tegra-libraries           \
    tensorrt-core             \
    tensorrt-plugins-prebuilt \
"

COMPATIBLE_MACHINE = "(tegra)"
