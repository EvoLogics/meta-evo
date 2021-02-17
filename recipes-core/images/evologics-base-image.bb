# Copyright (C) 2018 Maksym Komar <komar@evologics.de>
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "A base image for S2C Evomini2 board"
LICENSE = "MIT"

IMAGE_FEATURES += "ssh-server-dropbear package-management"

# kernel-dev ?
TOOLCHAIN_TARGET_TASK_append = " kernel-devsrc zlib libcsv"
# for sdmsh
TOOLCHAIN_TARGET_TASK_append = " readline bsd-headers-dev"

# for needed tcl-staticdev for libplconv for lbl-node
TOOLCHAIN_TARGET_TASK_append = " tcl-staticdev"

TOOLCHAIN_HOST_TASK_append = " nativesdk-erlang nativesdk-rebar nativesdk-erlang-modules-dev \
                               nativesdk-tcl-staticdev"


IMAGE_INSTALL = "\
    kernel-devicetree-compressed \
    \
    kernel-module-g-cdc \
    kernel-module-ppp-async \
    kernel-module-veth \
    kernel-module-bridge \
    kernel-module-iptable-nat \
    \
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
    ethtool \
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
    htop \
    \
    strace \
    \
    vim-xxd \
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
    erlang-inets \
    erlang-asn1 \
    "

DEPENDS += "\
    packagegroup-base-usbgadget \
    kexec-tools \
    \
    setserial \
    minicom\
    kermit \
    \
    pps-tools \
    rng-tools \
    \
    usbutils \
    \
    tmux \
    bash-completion \
    \
    procps \
    iperf3 \
    wget \
    dosfstools \
    \
    rsync \
    \
    can-utils \
    \
    tcpdump \
    ltrace \
    hdparm \
    \
    packagegroup-erlang-embedded \
    \
    tcllib \
    \
    fftw \
"

inherit core-image
