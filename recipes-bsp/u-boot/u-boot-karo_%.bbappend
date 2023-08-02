FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0101-ethsw-on.patch \
            file://0102-Enable-HUSH_PARSER-for-8133-35-37.patch \
            file://0103-Enable-HUSH_PARSER-for-8033-35-37.patch \
            file://0105-board-karo-print-debug.patch \
            file://0106-board-karo-disable-flexcan-quirks.patch \
           "

