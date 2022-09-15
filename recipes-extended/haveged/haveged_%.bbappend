FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
inherit ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}

SYSTEMD_PACKAGES:tegra194-evo = "${PN}"
SYSTEMD_SERVICE:${PN}:tegra194-evo = "haveged.service"

SRC_URI:append:tegra194-evo = " ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'file://haveged.service', '', d)}"

do_install:append:tegra194-evo() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
	install -d ${D}${systemd_system_unitdir}
	install -m 755 ${WORKDIR}/haveged.service ${D}${systemd_system_unitdir}/haveged.service
	sed -i -e "s,@SBIN_DIR@,${sbindir},g" ${D}${systemd_system_unitdir}/haveged.service
    fi
}

PACKAGE_ARCH:tegra194-evo = "${TEGRA_PKGARCH}"
