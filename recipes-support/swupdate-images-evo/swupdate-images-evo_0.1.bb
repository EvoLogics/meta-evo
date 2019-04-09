# Copyright (C) 2019 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Evologics SWU image"
DESCRIPTION = "Evologics recipe generating SWU image"
#SECTION = ""

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = ""

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or wahtever you need
SRC_URI = " \
    file://sw-description \
    "

# images to build before building swupdate image
IMAGE_DEPENDS = "evologics-base-image virtual/kernel"

# images and files that will be included in the .swu image
SWUPDATE_IMAGES = "at91bootstrap-sama5d2_roadrunner \
                   \
                   zImage \
                   sama5d2-roadrunner-evo-eth \
                   sama5d2-roadrunner-evo-rs232 \
                   sama5d2-roadrunner-evo-rs422 \
                   sama5d2-roadrunner-evo-rs485 \
                   sama5d2-roadrunner-evo-can \
                   \
                   evologics-base-image \
                   "

# a deployable image can have multiple format, choose one
SWUPDATE_IMAGES_FSTYPES[at91bootstrap-sama5d2_roadrunner] = ".bin"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[at91bootstrap-sama5d2_roadrunner]   = "1"

SWUPDATE_IMAGES_FSTYPES[zImage] = ".bin"
# SWUPDATE_IMAGES_FSTYPES[zImage-sama5d2-roadrunner-evo] = ".bin"
# SWUPDATE_IMAGES_NOAPPEND_MACHINE[zImage-sama5d2-roadrunner-evo]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-eth]   = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-eth]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-rs232]   = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-rs232]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-rs422] = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-rs422] = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-rs485] = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-rs485] = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-can]   = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-can]   = "1"

SWUPDATE_IMAGES_FSTYPES[evologics-base-image] = ".ubi"

inherit swupdate
