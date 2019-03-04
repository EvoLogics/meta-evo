# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "A base image for S2C Evomini2 board"
LICENSE = "MIT"

IMAGE_FEATURES += "ssh-server-dropbear package-management"

IMAGE_INSTALL = "\
    swupdate \
    swupdate-www \
    \
    libgpiod \
    evo-helpers \
    \
    packagegroup-core-boot \
    lrzsz \
    opkg \
    \
    busybox-udhcpd \
    busybox-udhcpc \
    busybox-hwclock \
    busybox-mdev \
    \
    i2c-tools \
    devmem2 \
    mtd-utils-ubifs \
    dtc \
    dtc-misc \
    mtd-utils \
    \
    sthttpd \
    iptables \
    \
    screen \
    \
    socat \
    readline \
    rlwrap \
    \
    strace \
    \
    pv \
    \
    libcsv \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    can-utils \
    iproute2 \
    \
    erlang \
    erlang-sasl \
    erlang-stdlib \
    erlang-kernel \
    erlang-erts \
    erlang-crypto \
    "

DEPENDS += "\
    packagegroup-base-usbgadget \
    kexec-tools \
    setserial \
    minicom\
    usbutils \
    \
    iperf3 \
    wget \
    dosfstools \
    \
    rsync \
    \
    can-utils \
    \
    packagegroup-erlang-embedded \
"

inherit core-image
