# Copyright (C) 2019 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Pure-C HTML5 parse"
HOMEPAGE = "https://github.com/google/gumbo-parser"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM="file://COPYING;md5=86d3f3a95c324c9479bd8986968f4327"
SECTION = "devel"

SRC_URI = "${DEBIAN_MIRROR}/main/g/gumbo-parser/gumbo-parser_0.10.1+dfsg.orig.tar.gz"

SRC_URI[md5sum] = "2d8f40ccf9d783c74459ec18db0771f5"
SRC_URI[sha256sum] = "bc03dfa4eeb959b9b1424c11c9450493774b575331e2b24d87aff39dee8c3594"

inherit autotools lib_package
