SUMMARY = "Systemd system configuration"
DESCRIPTION = "Systemd may require slightly different configuration for \
different machines.  For example, qemu machines require a longer \
DefaultTimeoutStartSec setting."

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/mx6ul-comm-module:"

SRC_URI:append_mx6ul-comm-module = " file://system.conf "

do_install:append_mx6ul-comm-module() {
  install -m 0644 ../system.conf ${D}${sysconfdir}/systemd/system.conf
}