SUMMARY = "Initramfs image for booting from SD card with cryptosetup"
DESCRIPTION = "This image decrupt SD card with rootfs and boot "
LICENSE = "MIT"
AUTHOR = "Maksym Komar <komar@evologics.de>"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit core-image

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

# avoid circular dependencies
EXTRA_IMAGEDEPENDS = ""
DISTRO_EXTRA_RRECOMMENDS = ""
KERNELDEPMODDEPEND = ""

# NOTE: working version with original cryptsetup
#PACKAGE_INSTALL = "busybox-initramfs cryptsetup libdevmapper initramfs-sd-rootfs-cryptsetup"

PACKAGE_INSTALL = "busybox-initramfs cryptsetup-plain-static initramfs-sd-rootfs-cryptsetup"

#FEATURE_PACKAGES_package-management = ""

# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""

IMAGE_LINGUAS = ""

FEED_DEPLOYDIR_BASE_URI = ""
LDCONFIGDEPEND = ""
IMAGE_ROOTFS_EXTRA_SPACE = "0"

VIRTUAL-RUNTIME_dev_manager = ""

# disable runtime dependency on run-postinsts -> update-rc.d
ROOTFS_BOOTSTRAP_INSTALL = ""

IMAGE_PREPROCESS_COMMAND += "prepare_rootfs;"

prepare_rootfs () {
    mkdir -p ${IMAGE_ROOTFS}/dev \
             ${IMAGE_ROOTFS}/sys \
             ${IMAGE_ROOTFS}/proc \
             ${IMAGE_ROOTFS}/mnt

    # Create device nodes expected by some kernels in initramfs
    # before even executing /init.
    mknod -m 622 ${IMAGE_ROOTFS}/dev/console c 5 1

    # FIXME: Find a way do not install alternatives
    # setup with cryptsetup-plain-static
    rm -rf ${IMAGE_ROOTFS}/etc \
           ${IMAGE_ROOTFS}/var \
           ${IMAGE_ROOTFS}/run \
           ${IMAGE_ROOTFS}/usr

#    # FIXME: Find a way do not install udev, libz, libblkid and alternatives
#    rm -rf ${IMAGE_ROOTFS}/etc \
#           ${IMAGE_ROOTFS}/var \
#           ${IMAGE_ROOTFS}/sbin/udevd \
#           ${IMAGE_ROOTFS}/lib/udev \
#           ${IMAGE_ROOTFS}/lib/modules \
#           ${IMAGE_ROOTFS}/usr/share \
#           ${IMAGE_ROOTFS}/usr/libexec \
#           ${IMAGE_ROOTFS}/usr/lib/libkmod.so* \
#           ${IMAGE_ROOTFS}/lib/libz.so* \
#           ${IMAGE_ROOTFS}/lib/libblkid.so* \
#           ${IMAGE_ROOTFS}/usr/bin/udevadm \
#           ${IMAGE_ROOTFS}/sbin/udevadm \
#           ${IMAGE_ROOTFS}/usr/lib/opkg/alternatives \
#           ${IMAGE_ROOTFS}/usr/bin/update-alternatives
}
