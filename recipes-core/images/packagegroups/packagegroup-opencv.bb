DESCRIPTION = "OpenCV for Image processing"

inherit packagegroup

RDEPENDS:${PN} = "            \
        opencv                \
        libopencv-core        \
        opencv-dev            \
        opencv-staticdev      \
"

COMPATIBLE_MACHINE = "(tegra)"
