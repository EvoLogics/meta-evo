SUMMARY = "A small image just capable of allowing a device to boot."

IMAGE_INSTALL = "packagegroup-core-boot"

IMAGE_INSTALL += "\
  iproute2 \
  bash tar monit procps util-linux \
  e2fsprogs e2fsprogs-resize2fs iputils coreutils \
  netcat-openbsd screen tmux socat rsync file \
  htop tcpdump \
  daemonize curl rlwrap ckermit iperf3 lrzsz \
  pps-tools pciutils \
  usbutils ethtool libgpiod \
  ltrace strace \
  chrony chronyc \
  lighttpd \
  "

IMAGE_INSTALL += "\
  ${CORE_IMAGE_EXTRA_INSTALL} \
  ${EVO_BASE_EXTRA_INSTALL} \
  "

LICENSE = "MIT"

inherit core-image

GLIBC_GENERATE_LOCALES = "en_US.UTF-8"
IMAGE_LINGUAS = "en-us"

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

ROOTFS_RO_UNNEEDED = ""
