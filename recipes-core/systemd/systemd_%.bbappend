FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/mx6ul-comm-module:"

SRC_URI_append_mx6ul-comm-module = " file://resolved.conf "

do_install_append_mx6ul-comm-module() {
  install -m 0644 ../resolved.conf ${D}${sysconfdir}/systemd/resolved.conf
}

PACKAGECONFIG_append_tegra194-evo = "            \
                                      cryptsetup \
                                      sysvinit   \
                                    "

do_install_append_tegra194-evo(){
  rm ${D}${systemd_system_unitdir}/usb-gadget.target
}
