SUMMARY = "A image for embedding to SD card for EvoLogics roadrunner board"
LICENSE = "MIT"
AUTHOR = "Maksym Komar <komar@evologics.de>"

IMAGE_FSTYPES = "ext4.luks.xz"
LUKS_IMAGE_SIZE = "300000"

require evologics-base-image.inc
inherit core-image
inherit cryptsetup-enc
