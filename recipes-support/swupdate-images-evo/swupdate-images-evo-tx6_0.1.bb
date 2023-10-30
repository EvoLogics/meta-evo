# Released under the MIT license (see COPYING.MIT for the terms)

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = ""

SWUPDATE_SIGNING = "CMS"
SWUPDATE_CMS_KEY = "${HOME}/cms_fwp.pem"
SWUPDATE_CMS_CERT = "${HOME}/cms_fwc.crt"
SWUPDATE_CMS_EXTRA_CERTS = "${HOME}/cms_mid.crt"
SWUPDATE_PASSWORD_FILE = "${HOME}/cms_pss.txt"

SRC_URI:append:tx6 = " \
  file://sw-description \
  file://swu-ab.sh \
  file://a-set.sh \
  file://b-set.sh \
"

SWUPDATE_IMAGES = " \
  evologics-base-image-mx \
  modules \
  uImage \
  imx6 \
"

EVOHW ?= "generic"
EVOSN ?= "0"

# images to build before building swupdate image
IMAGE_DEPENDS = "evologics-base-image-mx virtual/kernel"

SWUPDATE_IMAGES_FSTYPES[evologics-base-image-mx] = ".tar.bz2"
SWUPDATE_IMAGES_FSTYPES[modules] = ".tgz"
SWUPDATE_IMAGES_FSTYPES[imx6] = ".dtb"

inherit swupdate
