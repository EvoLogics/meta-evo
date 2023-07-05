# workaround against:
# /at91bootstrap_git.bb: Unable to get checksum for at91bootstrap SRC_URI entry sama5d2-roadrunner-evo_defconfig: file could not be found
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
