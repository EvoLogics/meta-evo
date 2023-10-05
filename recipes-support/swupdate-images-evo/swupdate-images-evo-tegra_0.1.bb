# Copyright (C) 2019 Llewellyn Fernandes <llewellyn.fernandes@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)
FILESEXTRAPATHS:prepend:tegra194-evo := "${THISDIR}/swupdate-images-evo-tegra194-evo:"

SUMMARY = "Evologics Jetson Module SWU image"
DESCRIPTION = "Evologics recipe generating SWU image for Jetson module "

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI:append:tegra194-evo = "   \
    file://sw-description         \
    file://swupdate-bl-update.sh  \
"

DEPENDS = ""

SWUPDATE_SIGNING = "RSA"
SWUPDATE_PRIVATE_KEY = "/work/private/evologics-tegra194-evo-priv.pem"

ROOTFS_DEVICE_PATH = "/dev/disk/by-partlabel"

inherit swupdate

# images to build before building swupdate image
IMAGE_DEPENDS = "core-image-minimal"

SWUPDATE_IMAGES = "core-image-minimal-${MACHINE}.tar.gz"

