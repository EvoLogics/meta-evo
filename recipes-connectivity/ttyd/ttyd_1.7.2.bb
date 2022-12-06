LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fe5f001c65f923d49dc96cce96ce935"

DEPENDS += "libuv json-c zlib libwebsockets"

SRC_URI = "git://github.com/tsl0922/ttyd.git;branch=main;protocol=https"
SRCREV = "020e2f3ded508d5fca95196a647fde09f05758b3"

S = "${WORKDIR}/git"
inherit cmake
