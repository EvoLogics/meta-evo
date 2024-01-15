# Copyright (C) 2019 Llewellyn Fernandes <llewellyn.fernandes@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Evologics Communication Module SWU image"
DESCRIPTION = "Evologics recipe generating SWU image for communication module "

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = ""

SWUPDATE_SIGNING = "RSA"
SWUPDATE_PRIVATE_KEY = "/work/private/evo-updater.pem"
SWUPDATE_PASSWORD_FILE = "/work/private/evo-updater.pass"

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or wahtever you need

SRC_URI:append:mx6ul-comm-module = "file://sw-description			\
									file://evo-swu-emb-system0.sh 	\
									file://evo-swu-emb-system1.sh 	\
"

SWUPDATE_IMAGES = "	evologics-base-image-mx							\
					imx6ul-comm-module-mx6ul-comm-module			\
					zImage											\
"

# images to build before building swupdate image
IMAGE_DEPENDS = "evologics-base-image-mx virtual/kernel"

SWUPDATE_IMAGES_FSTYPES[evologics-base-image-mx] = ".tar.gz"

SWUPDATE_IMAGES_FSTYPES[imx6ul-comm-module-mx6ul-comm-module] = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[imx6ul-comm-module-mx6ul-comm-module] = "1"

SWUPDATE_IMAGES_FSTYPES[zImage] = ".bin"


inherit swupdate
