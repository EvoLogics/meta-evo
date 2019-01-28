SUMMARY = "DUNE: Unified Navigation Environment"
DESCRIPTION = "DUNE: Unified Navigation Environment is a runtime environment \
for unmanned systems on-board software. \
It is used to write generic embedded software at the heart of the system, \
e.g. code or control, navigation, communication, sensor and \
actuator access, etc. \
It provides an operating-system and architecture independent platform \
abstraction layer, written in C++, enhancing portability among different CPU \
architectures and operating systems."

HOMEPAGE = "http://lsts.fe.up.pt/toolchain/dune"

# DUNE uses _modified_ version of the EUPL-1.1 for non-commercial purposes.
# For detailed licensing terms and conditions please see https://github.com/LSTS/dune/
LICENSE = "EUPL-1.1"
LIC_FILES_CHKSUM = "file://LICENCE.md;md5=b10079f5f43a149da9e871f5aebe9a4b"

PR = "r0"

SRCREV = "84a81558640b06357028be6ae64bad12fe4bf516"

S = "${WORKDIR}/git"

SRC_URI = " \
    git://github.com/LSTS/dune.git \
    "

PACKAGES += "${PN}-scripts ${PN}-www ${PN}-etc ${PN}-tools"

FILES_${PN} = "${prefix}/bin/dune ${prefix}/bin/dune-launcher"
FILES_${PN}-scripts += "${prefix}/scripts"
FILES_${PN}-www += "${prefix}/www"
FILES_${PN}-etc += "${prefix}/etc"
FILES_${PN}-tools = "${prefix}/bin/dune-*"

inherit cmake
