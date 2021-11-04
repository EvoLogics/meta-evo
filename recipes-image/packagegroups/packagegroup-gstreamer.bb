DESCRIPTION = "Gstreamer tools and plugins"

inherit packagegroup

RDEPENDS_${PN} = "                \
    gstreamer1.0                  \
    gstreamer1.0-libav            \
    gstreamer1.0-meta-base        \
    gstreamer1.0-omx              \
    gstreamer1.0-plugins-bad      \
    gstreamer1.0-plugins-good     \
    gstreamer1.0-plugins-ugly     \
    gstreamer1.0-plugins-base     \
    gstreamer1.0-plugins-tegra    \
    gstreamer1.0-rtsp-server      \
    gstreamer1.0-vaapi            \
"

COMPATIBLE_MACHINE = "(tegra)"