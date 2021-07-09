# Copyright (C) 2019 Llewellyn Fernandes <llewellyn.fernandes@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Evologics Communication Module SWU image"
DESCRIPTION = "Evologics recipe generating SWU image for communication module "
#SECTION = ""

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/${PN}:"

DEPENDS = ""

SWUPDATE_SIGNING = "RSA"
SWUPDATE_PRIVATE_KEY = "/work/evo-updater.pem"
SWUPDATE_PASSWORD_FILE = "/work/evo-updater.pass"

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or wahtever you need
SRC_URI_append_mx6ul-comm-module = " 	        		\
            file://sw-description 		        	\
            file://evo-swu-emb-system0.sh 			\
            file://evo-swu-emb-system1.sh     			\
"
SWUPDATE_IMAGES = "core-image-minimal   			\
                   imx6ul-comm-module-mx6ul-comm-module   	\
                   zImage               			\
"

SWUPDATE_IMAGES_FSTYPES[core-image-minimal] = ".tar.gz"

SWUPDATE_IMAGES_FSTYPES[imx6ul-comm-module-mx6ul-comm-module] = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[imx6ul-comm-module-mx6ul-comm-module] = "1"

SWUPDATE_IMAGES_FSTYPES[zImage] = ".bin"


inherit swupdate
