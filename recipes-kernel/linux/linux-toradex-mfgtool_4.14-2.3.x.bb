# Copyright (C) 2014-2018 O.S. Systems Software LTDA.
# Copyright (C) 2014-2016 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"

require recipes-kernel/linux/linux-toradex_${PV}.bb
require recipes-kernel/linux/linux-toradex-append.inc
require recipes-kernel/linux/linux-mfgtool.inc

KERNEL_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
MODULE_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
do_package[vardepsexclude] = "DATETIME"

SRC_URI_append = " \
  file://mfgtool.cfg \
  "
