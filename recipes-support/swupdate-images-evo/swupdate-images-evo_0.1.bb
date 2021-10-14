# Copyright (C) 2019 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Evologics SWU image"
DESCRIPTION = "Evologics recipe generating SWU image"
#SECTION = ""

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.GPLv2;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = ""

SWUPDATE_SIGNING = "RSA"
SWUPDATE_PRIVATE_KEY = "/work/private/evo-updater.pem"
SWUPDATE_PASSWORD_FILE = "/work/private/evo-updater.pass"

# Add all local files to be added to the SWU
# sw-description must always be in the list.
# You can extend with scripts or wahtever you need
SRC_URI = " \
    file://sw-description \
    file://evo-swu-emb.sh \
    "

# images to build before building swupdate image
IMAGE_DEPENDS = "evologics-base-image virtual/kernel"

# images and files that will be included in the .swu image
SWUPDATE_IMAGES = "at91bootstrap-sama5d2_roadrunner \
                   \
                   zImage \
                   sama5d2-roadrunner-evo \
                   sama5d2-roadrunner-evo-tiny \
                   sama5d2-roadrunner-evo-tiny-eth \
                   sama5d2-roadrunner-evo-tiny-rs232 \
                   sama5d2-roadrunner-evo-tiny-rs422 \
                   sama5d2-roadrunner-evo-tiny-rs485 \
                   sama5d2-roadrunner-evo-tiny-can \
                   \
                   sama5d2-roadrunner-evo-r2 \
                   sama5d2-roadrunner-evo-r2-eth \
                   sama5d2-roadrunner-evo-r2-rs232 \
                   sama5d2-roadrunner-evo-r2-rs422 \
                   sama5d2-roadrunner-evo-r2-rs485 \
                   sama5d2-roadrunner-evo-r2-can \
                   \
                   evologics-base-image \
                   "

# a deployable image can have multiple format, choose one
SWUPDATE_IMAGES_FSTYPES[at91bootstrap-sama5d2_roadrunner] = ".bin"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[at91bootstrap-sama5d2_roadrunner]   = "1"

SWUPDATE_IMAGES_FSTYPES[zImage] = ".bin"
# SWUPDATE_IMAGES_FSTYPES[zImage-sama5d2-roadrunner-evo] = ".bin"
# SWUPDATE_IMAGES_NOAPPEND_MACHINE[zImage-sama5d2-roadrunner-evo]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo]   = ".dtb"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny-eth]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny-eth]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny-rs232]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny-rs232]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny-rs422]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny-rs422]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny-rs485]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny-rs485]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-tiny-can]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-tiny-can]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2-eth]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2-eth]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2-rs232]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2-rs232]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2-rs422]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2-rs422]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2-rs485]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2-rs485]   = "1"

SWUPDATE_IMAGES_FSTYPES[sama5d2-roadrunner-evo-r2-can]   = ".dtbo"
SWUPDATE_IMAGES_NOAPPEND_MACHINE[sama5d2-roadrunner-evo-r2-can]   = "1"

SWUPDATE_IMAGES_FSTYPES[evologics-base-image] = ".ubi"

inherit swupdate
