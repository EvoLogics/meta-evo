SUMMARY = "A base image for QSPI flash for EvoLogics roadrunner board"
LICENSE = "MIT"
AUTHOR = "Maksym Komar <komar@evologics.de>"

IMAGE_FSTYPES = "tar.xz ubi"

# evologics-base-image (.ubi) =>               # rootfs, vol1, vol2, data partitions
#   evologics-base-sd-image (.wic) =>          # rootfs with sandbox
#       evologics-base-luks-image (.ext4.luks) # only rootfs, used in .wic
# 
# evologics-data-image (.ubi) # no rootfs for 16MB model.
#                             # only vol1, vol2, data partition

DEPENDS += "evologics-base-sd-image evologics-data-image"

do_image_complete[depends] += "evologics-base-sd-image:do_image_complete"
do_image_complete[depends] += "evologics-data-image:do_image_complete"
do_clean[depends]          += "evologics-base-sd-image:do_clean"
do_clean[depends]          += "evologics-data-image:do_clean"
do_cleanall[depends]       += "evologics-base-sd-image:do_cleanall"
do_cleanall[depends]       += "evologics-data-image:do_cleanall"

MKUBIFS_VOLUMES="etc1 etc2 data rootfs"
MKUBIFS_VOLUME_SIZE[data] = "7MiB"
MKUBIFS_VOLUME_ARGS[data] = "-m 1 -e 65408 -c 103 -F"

require evologics-base-image.ubi.inc
require evologics-base-image.inc

inherit core-image
