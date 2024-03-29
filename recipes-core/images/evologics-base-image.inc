IMAGE_FEATURES += "ssh-server-dropbear package-management"

##################### install target packages ##################################
TOOLCHAIN_TARGET_TASK_append = " opkg-arch-config"
# kernel-dev ?
TOOLCHAIN_TARGET_TASK_append = " kernel-devsrc zlib zlib-staticdev libcsv"
# for sdmsh
TOOLCHAIN_TARGET_TASK_append = " readline readline-staticdev \
                                 ncurses-staticdev musl-staticdev \
                                 bsd-headers-dev"

# for needed tcl-staticdev for libplconv for lbl-node
TOOLCHAIN_TARGET_TASK_append = " tcl-staticdev"

##################### install nativesdk packages ##################################
TOOLCHAIN_HOST_TASK_append = " nativesdk-erlang nativesdk-rebar3 \
                               nativesdk-erlang-modules-dev \
                               nativesdk-tcl-staticdev"

TOOLCHAIN_HOST_TASK_append = " nativesdk-opkg-arch-config"

IMAGE_INSTALL = "\
    kernel-devicetree-compressed \
    \
    kernel-module-g-cdc \
    kernel-module-ppp-async \
    kernel-module-veth \
    kernel-module-bridge \
    kernel-module-nft-nat \
    kernel-module-nf-tables-ipv4 \
    kernel-module-nft-chain-nat-ipv4 \
    kernel-module-nf-nat-ipv4 \
    kernel-module-xt-tcpudp \
    kernel-module-nft-compat \
    kernel-module-nft-meta \
    kernel-module-nft-counter \
    kernel-module-xt-nat \
    kernel-module-nf-tables-ipv4 \
    kernel-module-nft-chain-nat-ipv4 \
    kernel-module-nf-tables \
    kernel-module-nfnetlink \
    kernel-module-nf-conntrack-ipv4 \
    kernel-module-nf-defrag-ipv4 \
    kernel-module-nf-nat-ipv4 \
    kernel-module-nf-nat \
    kernel-module-nf-conntrack \
    kernel-module-x-tables \
    \
    swupdate \
    swupdate-www \
    openssh-sftp-server \
    \
    libgpiod \
    evo-helpers \
    \
    packagegroup-core-boot \
    distro-feed-configs \
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
    lrzsz \
    gkermit \
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
    tclreadline \
    \
    erlang \
    erlang-sasl \
    erlang-stdlib \
    erlang-kernel \
    erlang-erts \
    erlang-crypto \
    erlang-inets \
    erlang-asn1 \
    \
    liblxc-bin-lxc-start \
    liblxc-bin-lxc-stop  \
    liblxc-bin-lxc-info  \
    liblxc-bin-lxc-attach  \
    liblxc-bin-lxc-console  \
    ppp \
    \
    cryptsetup-plain \
    "

DEPENDS += "\
    packagegroup-base-usbgadget \
    kexec-tools \
    \
    setserial \
    minicom\
    ckermit \
    ekermit \
    lgpio \
    \
    pps-tools \
    rng-tools \
    \
    usbutils \
    \
    jq \
    \
    tmux \
    bash-completion \
    \
    procps \
    iperf3 \
    \
    wget \
    rsync \
    \
    dosfstools \
    \
    can-utils \
    \
    tcpdump \
    ltrace \
    valgrind \
    hdparm \
    \
    packagegroup-erlang-embedded \
    \
    tcllib \
    tclx \
    tcludp \
    \
    fftw \
"

