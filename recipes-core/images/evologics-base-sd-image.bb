SUMMARY = "A image for SD card for EvoLogics roadrunner board"
LICENSE = "MIT"
AUTHOR = "Maksym Komar <komar@evologics.de>"

inherit core-image

IMAGE_FSTYPES = "wic.xz"

DEPENDS += "evologics-base-luks-image"
do_image_wic[depends] += "evologics-base-luks-image:do_image_complete"
do_clean[depends] += "evologics-base-luks-image:do_clean"
do_cleanall[depends] += "evologics-base-luks-image:do_cleanall"


IMAGE_CMD_wic_prepend () {
    if [ -e ${DEPLOY_DIR_IMAGE}/evologics-base-luks-image-${MACHINE}.ext4.luks.xz ]; then
        xz -fkd ${DEPLOY_DIR_IMAGE}/evologics-base-luks-image-${MACHINE}.ext4.luks.xz
    fi
}

IMAGE_CMD_wic_append () {
    if [ -e ${DEPLOY_DIR_IMAGE}/evologics-base-luks-image-${MACHINE}.ext4.luks.xz ]; then
        rm -f ${DEPLOY_DIR_IMAGE}/evologics-base-luks-image-${MACHINE}.ext4.luks
    fi
}

WKS_FILE = "${MACHINE}.wks"

