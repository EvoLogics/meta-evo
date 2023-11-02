SUMMARY = "SFTPGo"
DESCRIPTION = "Fully featured and highly configurable SFTP server with optional HTTP/S, FTP/S and WebDAV support."
GO_IMPORT = "github.com/drakkan/sftpgo"

HOMEPAGE = "https://sftpgo.com/"
LICENSE = "AGPL-3.0"
#LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=0d08d799c3aa0d044526e77b67c5eebc"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/AGPL-3.0;md5=73f1eb20517c55bf9493b7dd6e480788"
SRC_URI = " \
  https://github.com/drakkan/sftpgo/releases/download/v${PV}/${BPN}_v${PV}_linux_${TARGET_ARCH}.tar.xz \
  file://${BPN}.init \
  file://${BPN}.default \
  file://${BPN}.service \
  file://${BPN}.json \
  "

# No spaces here! 
SRC_URI[sha256sum] = "\
${@bb.utils.contains('TARGET_ARCH', 'x86_64', 'f2b18ff11d4720c9dc1feb9c688c6b1030afbfda4c2ae553fec58d92e97384e1', '', d)}\
${@bb.utils.contains('TARGET_ARCH', 'armv7',  '5fa841087f55d7f518e07a2bff8293afca1dce6ae100e42ada89851621eb29db', '', d)}\
"

#git://${GO_IMPORT};branch=${SRCBRANCH};protocol=https
#SRCREV = "66c14bebd8fb5d19201a74d74a93f3c45a783bb3"
#SRCBRANCH = "2.3.x"
# dunfell go is too old to compile...
#GO_INSTALL = "${GO_IMPORT}"
#inherit go-mod
#GOBUILDFLAGS:append = " -tags nogcs,nos3,noazblob,nobolt,nomysql,nopgsql,nometrics,unixcrypt "

FILES:${PN} += "\
  /opt \
  "

INSANE_SKIP:${PN} = "already-stripped"

do_configure() {
  :
}

do_compile() {
  :
}

do_install() {
  install -d ${D}/opt/${BPN}
    install -m 0755 ${WORKDIR}/${BPN} ${D}/opt/${BPN}/
    cp -r ${WORKDIR}/openapi ${D}/opt/${BPN}/
    cp -r ${WORKDIR}/static ${D}/opt/${BPN}/

  install -d ${D}/${sysconfdir}/${BPN}
    install -m 0644 ${WORKDIR}/${BPN}.json ${D}/${sysconfdir}/${BPN}/
  
  install -d ${D}/${sysconfdir}/default
    install -m 0644 ${WORKDIR}/${BPN}.default ${D}/${sysconfdir}/default/${BPN}

  if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
    install -d ${D}${systemd_unitdir}/system/
      install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_unitdir}/system/${BPN}.service
  else
    install -d ${D}/${sysconfdir}/init.d/
      install -m 0755 ${WORKDIR}/${BPN}.init ${D}/${sysconfdir}/init.d/${BPN}
  fi
}
