#####################################################################
#####################################################################
# 16 MiB qspi flash
# TODO: calculate 65536 - 128
#
# python -c 'print (0x000001000000 - 0x000000420000) / 65536'
# 190
#
#MKUBIFS_ARGS = "-m 1 -e 65408 -c 190 -F"
#UBINIZE_ARGS = "-m 1 -p 64KiB -Q 798393222"
#####################################################################
# 32 MiB qspi flash
# TODO: calculate 65536 - 128
#
# python -c 'print (0x000002210000 - 0x000000420000)/65536'
# 479
#
#MKUBIFS_ARGS = "-m 1 -e 65408 -c 479 -F"
#UBINIZE_ARGS = "-m 1 -p 64KiB"
#####################################################################
# [    1.900000] 0x000000420000-0x000004000000 : "rootfs"
#
# python -c 'print (0x000004000000 - 0x000000420000) / 65536'
# 958
# LEBs count: -c 958
#
# python -c 'print hex(0x000004000000 - 0x000000420000)'
# 0x3be0000 .dts
# reg = <0x00420000 0x03be0000>; // 64 MiB
# 64 MiB qspi flash
#MKUBIFS_ARGS = "-m 1 -e 65408 -c 958 -F"
#UBINIZE_ARGS = "-m 1 -p 64KiB"
#####################################################################
## 128 MiB qspi flash
MKUBIFS_ARGS = "-m 1 -e 65408 -c 1976 -F"
UBINIZE_ARGS = "-m 1 -p 64KiB"
#####################################################################
#####################################################################

#[   12.680000] UBIFS (ubi0:0): UBIFS: mounted UBI device 0, volume 0, name "etc1", R/O mode
#[   12.680000] UBIFS (ubi0:0): LEB size: 65408 bytes (63 KiB), min./max. I/O unit sizes: 8 bytes/256 bytes
#[   12.690000] UBIFS (ubi0:0): FS size: 784896 bytes (0 MiB, 12 LEBs), journal size 588673 bytes (0 MiB, 8 LEBs)
#[   12.700000] UBIFS (ubi0:0): reserved for root: 0 bytes (0 KiB)
#[   12.710000] UBIFS (ubi0:0): media format: w4/r0 (latest is w4/r0), UUID F78BBE14-4EC5-4426-870A-BF01DD2578FA, small LPT model
#[   12.820000] UBIFS (ubi0:1): UBIFS: mounted UBI device 0, volume 1, name "etc2", R/O mode
#[   12.820000] UBIFS (ubi0:1): LEB size: 65408 bytes (63 KiB), min./max. I/O unit sizes: 8 bytes/256 bytes
#[   12.830000] UBIFS (ubi0:1): FS size: 784896 bytes (0 MiB, 12 LEBs), journal size 588673 bytes (0 MiB, 8 LEBs)

# with sandbox did't work
#| Error: min. I/O unit was not specified (use -h for help)
#MKUBIFS_VOLUMES="etc1 etc2 data rootfs sandbox"
MKUBIFS_VOLUME_SIZE[etc1] = "2MiB"
MKUBIFS_VOLUME_ARGS[etc1] = "-m 1 -e 65408 -c 22 -F"
MKUBIFS_VOLUME_SIZE[etc2] = "2MiB"
MKUBIFS_VOLUME_ARGS[etc2] = "-m 1 -e 65408 -c 22 -F"

JFFS2_ERASEBLOCK = "0x1000"
EXTRA_IMAGECMD:jffs2 = "${JFFS2_ENDIANNESS} --eraseblock=${JFFS2_ERASEBLOCK} --no-cleanmarkers"
