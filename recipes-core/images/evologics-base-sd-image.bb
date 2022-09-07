SUMMARY = "A images for SD card for EvoLogics roadrunner board"
AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "evologics-base-sd-image-4g evologics-base-sd-image-32g"

do_build[depends]    += "evologics-base-sd-image-4g:do_image_complete"
do_build[depends]    += "evologics-base-sd-image-32g:do_image_complete"
do_clean[depends]    += "evologics-base-sd-image-4g:do_clean"
do_clean[depends]    += "evologics-base-sd-image-32g:do_clean"
do_cleanall[depends] += "evologics-base-sd-image-4g:do_cleanall"
do_cleanall[depends] += "evologics-base-sd-image-32g:do_cleanall"

