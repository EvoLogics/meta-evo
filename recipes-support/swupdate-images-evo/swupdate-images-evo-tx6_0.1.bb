# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = ""

SWUPDATE_SIGNING = "RSA"
SWUPDATE_PRIVATE_KEY = "${HOME}/swu-prv.pem"
SWUPDATE_PASSWORD_FILE = "${HOME}/swu-pss.txt"

SRC_URI_append_tx6 = " \
  file://sw-description \
  file://swu-ab.sh \
  file://a-set.sh \
  file://b-set.sh \
"

SWUPDATE_IMAGES = " \
  core-image-minimal \
  modules \
  uImage \
  imx6 \
"

EVOHW ?= "generic"
EVOSN ?= "0"

# images to build before building swupdate image
IMAGE_DEPENDS = "core-image-minimal virtual/kernel"

SWUPDATE_IMAGES_FSTYPES[core-image-minimal] = ".tar.bz2"
SWUPDATE_IMAGES_FSTYPES[modules] = ".tgz"
SWUPDATE_IMAGES_FSTYPES[imx6] = ".dtb"

inherit swupdate
