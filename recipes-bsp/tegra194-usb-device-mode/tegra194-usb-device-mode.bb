DESCRIPTION = "Configuration for setting up Tegra194 USB device mode gadgets"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://70-usb-gadget.network \
    file://70-usbbr0.network \
    file://usbbr0.netdev \
    file://98-usb-gadget-tty.rules \
"

COMPATIBLE_MACHINE = "(tegra)"

inherit features_check

REQUIRED_DISTRO_FEATURES = "systemd"

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${S}/usbbr0.netdev ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${S}/70-usbbr0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${S}/70-usb-gadget.network ${D}${sysconfdir}/systemd/network/
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/98-usb-gadget-tty.rules ${D}${sysconfdir}/udev/rules.d/
}

RDEPENDS_${PN} = "libusbgx"

