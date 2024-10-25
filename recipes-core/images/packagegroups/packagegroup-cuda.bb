DESCRIPTION = "Cuda tools and libraries for tegra"

inherit packagegroup

RDEPENDS:${PN} = "            \
    cuda-compiler             \
    cuda-gdb                  \
    cuda-nvrtc                \
    cuda-nvgraph              \
    cuda-cudart               \
    cuda-libraries            \
    cuda-nvml                 \
    cuda-cuobjdump            \
    cuda-cupti                \
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
    libcusolver               \
    libcufft                  \ 
    libcurand                 \
    libcusparse               \
    libnpp                    \
    tensorrt-core             \
    tensorrt-plugins-prebuilt \
    tensorrt-plugins-dev      \
"

COMPATIBLE_MACHINE = "(tegra)"
