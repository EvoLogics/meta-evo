SUMMARY = "The Dynamic Compact Control Language (DCCL)"
DESCRIPTION = "The Dynamic Compact Control Language (DCCL) is a language for marshalling (or roughly analogously: source encoding or compressing) object-based messages for extremely low throughput network links"
HOMEPAGE = "http://libdccl.org"
SECTION = "devel"

AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "GPLv2 & LGPLv2.1 & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fcbf15b48fc20083a9a865c4b57a76f0 \
                    file://scripts/clang-format-hooks/COPYING;md5=ea40b5e96d17860e79bfab21f006c3fd"

SRC_URI = "git://github.com/GobySoft/dccl.git;branch=4.0;protocol=https"

PV = "4.2.1"
SRCREV = "e3e426728677be19d806ef9ba22b18e53bca29d1"

S = "${WORKDIR}/git"

# trying build support with dynamic_condition (need lua). but there is error
# ld: ../../../lib/libdccl.so.4.2.1+0+gite3e42672-dirty: undefined reference to `luaopen_pb(lua_State*)'
#DEPENDS = "boost protobuf dccl-native lua"

DEPENDS = "boost protobuf dccl-native"

inherit cmake

# work around of cmake + gcc with option isystem, which could not find stdlib.h
EXTRA_OECMAKE  = "-DCMAKE_CXX_IMPLICIT_INCLUDE_DIRECTORIES:PATH='${STAGING_INCDIR}'"
#                  -DCMAKE_GENERATOR:INTERNAL='Unix Makefiles'"

FILES_${PN}-dev += "${bindir}/analyze_dccl \
                    ${bindir}/protoc-gen-dccl \
                    ${datadir}/${PN} \
                    "

BBCLASSEXTEND = "native nativesdk"


