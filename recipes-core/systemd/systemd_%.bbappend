FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

PACKAGECONFIG_append_tegra194-evo = "            \
                                      cryptsetup \
                                      sysvinit   \
                                    "

do_install_append_tegra194-evo(){
  rm ${D}${systemd_system_unitdir}/usb-gadget.target
}