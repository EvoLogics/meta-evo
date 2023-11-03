SUMMARY = "websocat"
DESCRIPTION = "Netcat, curl and socat for WebSockets."

HOMEPAGE = "https://github.com/vi/websocat"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = " \
  https://github.com/vi/websocat/releases/download/v${PV}/${BPN}.${TARGET_ARCH}-unknown-linux-musl \
  "

# No spaces here! 
SRC_URI[sha256sum] = "\
${@bb.utils.contains('TARGET_ARCH', 'x86_64', 'e4da4f6c00402e893f3e3120c62e16b61a84aaa78f772b3e155f319f5210d2c6', '', d)}\
${@bb.utils.contains('TARGET_ARCH', 'armv7',  'fixme', '', d)}\
"

INSANE_SKIP:${PN} = "already-stripped"

do_configure() {
  :
}

do_compile() {
  # copy not to fail if 'rebuilt'
  cp ${WORKDIR}/${BPN}.${TARGET_ARCH}-unknown-linux-musl ${WORKDIR}/${BPN}
}

do_install() {
  install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/${BPN} ${D}/${bindir}/${BPN}
}
