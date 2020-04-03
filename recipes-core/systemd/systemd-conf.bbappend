SUMMARY = "Systemd system configuration"
DESCRIPTION = "Systemd may require slightly different configuration for \
different machines.  For example, qemu machines require a longer \
DefaultTimeoutStartSec setting."

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install_append_mx6ul-comm-module() {
	# Change DefaultTimeoutStopSec from 90 to 5 seconds
	echo "DefaultTimeoutStopSec = 5s" >> ${D}${sysconfdir}/systemd/system.conf
}
