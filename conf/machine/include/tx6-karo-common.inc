# Provides the Ka-Ro TX6 common settings

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv7a/tune-cortexa9.inc

SERIAL_CONSOLE = "115200 ttymxc0"

UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = "u-boot.bin"
UBOOT_BINARY="u-boot.bin"

PREFERRED_PROVIDER_u-boot ?= "u-boot-karo"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-karo-fw-utils"
PREFERRED_PROVIDER_u-boot-mfgtool ?= "u-boot-karo-mfgtool"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-karo"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-toradex"
PREFERRED_PROVIDER_linux-mfgtool ?= "linux-toradex-mfgtool"

MACHINE_FIRMWARE:append:mx6 = " linux-firmware-ath6k"
MACHINE_FEATURES += " pci wifi bluetooth"
IMAGE_FSTYPES:remove = "sdcard"
IMAGE_FSTYPES = "tar.bz2"

TXBASE ?= "mb7"
