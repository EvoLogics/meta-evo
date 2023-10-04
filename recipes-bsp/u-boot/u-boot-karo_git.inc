DESCRIPTION = "Das U-Boot for Ka-Ro electronics TX Computer-On-Modules."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"

PV = "v2015.10-rc2+git${SRCPV}"

SRCREV = "20bdf53cb7ed384fc831a38370143afe58fb037d"
SRCBRANCH = "master"
SRC_URI = " \
  git://github.com/karo-electronics/karo-tx-uboot.git;branch=${SRCBRANCH} \
  file://mx6-soc-l2en.patch \
	file://mx6-clock-div.patch \
  file://0004-tools-env-include-compiler.h.patch \
  file://0005-tools-fix-cross-compiling-tools-when-HOSTCC-is-overr.patch \
	"

S = "${WORKDIR}/git"

UBOOT_INITIAL_ENV = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE  = "(tx6[qsu]-.*|txul-.*)"
