DESCRIPTION = "OpenCV for Image processing"

inherit packagegroup

RDEPENDS_${PN} = "            \
        opencv                \
        opencv-dev            \
        opencv-staticdev      \
        libopencv-core        \
"

COMPATIBLE_MACHINE = "(tegra)"
