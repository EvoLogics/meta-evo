LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fe5f001c65f923d49dc96cce96ce935"

DEPENDS += "libuv json-c zlib libwebsockets"

SRC_URI = "git://github.com/tsl0922/ttyd.git;branch=main;protocol=https \
           file://ttyd.init \
          "

SRCREV = "020e2f3ded508d5fca95196a647fde09f05758b3"

S = "${WORKDIR}/git"
inherit cmake

do_install_append() {
  if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/ttyd.service ${D}${systemd_system_unitdir}/
  else
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/ttyd.init ${D}${sysconfdir}/init.d/ttyd
  fi
}
