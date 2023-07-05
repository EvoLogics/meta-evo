SUMMARY = "A data image for QSPI flash for EvoLogics roadrunner board"
LICENSE = "MIT"
AUTHOR = "Maksym Komar <komar@evologics.de>"

IMAGE_FSTYPES = "ubi"

MKUBIFS_VOLUMES="etc1 etc2 data"
MKUBIFS_VOLUME_ARGS[data] = "-m 1 -e 65408 -c 120 -F"
require evologics-base-image.ubi.inc

inherit core-image
