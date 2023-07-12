FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/mx6ul-comm-module:"

SRC_URI_append_mx6ul-comm-module = " file://resolved.conf "

do_install_append_mx6ul-comm-module() {
  install -m 0644 ../resolved.conf ${D}${sysconfdir}/systemd/resolved.conf
}