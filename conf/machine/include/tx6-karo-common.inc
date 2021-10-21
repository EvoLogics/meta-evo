# Provides the Ka-Ro TX6 common settings

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa9.inc

SERIAL_CONSOLE = "115200 ttymxc0"

UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = "u-boot.bin"
UBOOT_BINARY="u-boot.bin"

PREFERRED_PROVIDER_u-boot ?= "u-boot-karo"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot-karo"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-karo"

MACHINE_FIRMWARE_append_mx6 = " linux-firmware-ath6k"
MACHINE_FEATURES += " pci wifi bluetooth"
IMAGE_FSTYPES_remove = "sdcard"
IMAGE_FSTYPES = "tar.bz2"

TXBASE ?= "mb7"