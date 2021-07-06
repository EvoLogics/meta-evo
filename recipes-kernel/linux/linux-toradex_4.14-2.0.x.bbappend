FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PR .= ".0"

SRCREV_sdma-imx6q = "55edf5202154de59ee1c6a5b6b6ba6fa54571515"
SRC_URI[sdma-imx6q.md5sum] = "03405e6d82f1fe8ae1517d0a294027dd"
SRC_URI[sdma-imx6q.sha256sum] = "7790c161b7e013a9dbcbffb17cc5d4cb63d952949a505647e4679f02d04c4784"

KERNEL_IMAGETYPE = "uImage"

do_configure_append() {
    CFG="$(ls ${WORKDIR}/*.cfg 2> /dev/null || true)"
    test -n "$CFG" && cat ${WORKDIR}/*.cfg >> ${B}/.config
    return 0
}

########################
### Colibri Specific ###
########################

SRC_URI_append_colibri-imx6-iris-evo = "\
  file://0001-Define-SD_1_8.patch \
  "

do_patch_prepend_colibri-imx6-iris-evo() {
    s = d.getVar("S")
    src = oe.path.join(s, "arch/arm/boot/dts/imx6dl-colibri-eval-v3.dts")
    dest = oe.path.join(s, "arch/arm/boot/dts/imx6dl-colibri-eval-v3-sd18.dts")

    bb.utils.copyfile(src, dest)
}

####################
### TX6 Specific ###
####################

FILESEXTRAPATHS_prepend_tx6 := "${THISDIR}/linux-karo-common:"

SRC_URI_append_tx6 = "\
  file://defconfig \
  "

SRC_URI_append_tx6 = "\
  https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/imx/sdma/sdma-imx6q.bin?id=${SRCREV_sdma-imx6q};subdir=${S}/firmware/imx/sdma/;downloadfilename=sdma-imx6q.bin;name=sdma-imx6q \
  "

SRC_URI_append_tx6 = "\
  file://imx6-tx6-evobb.cfg \
  file://imx6-${MACHINE}.dts;subdir=${S}/arch/arm/boot/dts \
  file://imx6-tx6-common.dtsi;subdir=${S}/arch/arm/boot/dts \
  file://imx6-tx6-evobb.dtsi;subdir=${S}/arch/arm/boot/dts \
  file://imx6qdl-tx6.dtsi;subdir=${S}/arch/arm/boot/dts \
  file://imx6qdl-tx6-lcd.dtsi;subdir=${S}/arch/arm/boot/dts \
  file://imx6qdl-tx6-lvds.dtsi;subdir=${S}/arch/arm/boot/dts \
  "
