FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}/mx6ul-comm-module:"

SRC_URI:append:mx6ul-comm-module = " file://resolved.conf "

do_install:append:mx6ul-comm-module() {
  install -m 0644 ../resolved.conf ${D}${sysconfdir}/systemd/resolved.conf
}

PACKAGECONFIG:append_tegra194-evo = "            \
                                      cryptsetup \
                                      sysvinit   \
                                    "

do_install:append_tegra194-evo(){
  rm ${D}${systemd_system_unitdir}/usb-gadget.target
}
