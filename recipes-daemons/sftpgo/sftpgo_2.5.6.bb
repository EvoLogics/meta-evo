SUMMARY = "SFTPGo"
DESCRIPTION = "Fully featured and highly configurable SFTP server with optional HTTP/S, FTP/S and WebDAV support."
GO_IMPORT = "github.com/drakkan/sftpgo"

HOMEPAGE = "https://sftpgo.com/"
LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=0d08d799c3aa0d044526e77b67c5eebc"
SRC_URI = " \
  git://${GO_IMPORT};branch=${SRCBRANCH};protocol=https \
  file://${BPN}.init \
  file://${BPN}.default \
  file://${BPN}.service \
  file://${BPN}.json \
  "

INSANE_SKIP:${PN}-dev = "file-rdeps"

SRCREV = "a6a92f0d69b8cf5e72e38ffa5e693057aa2dc776"
SRCBRANCH = "2.5.x"
GO_INSTALL = "${GO_IMPORT}"
inherit go-mod
GOBUILDFLAGS:append = " -tags nogcs,nos3,noazblob,nobolt,nomysql,nopgsql,nometrics"

FILES:${PN} += "\
  ${datadir} \
  "

do_install:append() {
  install -d ${D}/${datadir}/${BPN}
    cp -r ${S}/src/${GO_IMPORT}/openapi ${D}/${datadir}/${BPN}/
    cp -r ${S}/src/${GO_IMPORT}/static ${D}/${datadir}/${BPN}/
    cp -r ${S}/src/${GO_IMPORT}/templates ${D}/${datadir}/${BPN}/

  install -d ${D}/${sysconfdir}/${BPN}
    install -m 0644 ${WORKDIR}/${BPN}.json ${D}/${sysconfdir}/${BPN}/
    ln -s ${datadir}/sftpgo/openapi ${D}/${sysconfdir}/${BPN}/openapi
    ln -s ${datadir}/sftpgo/static ${D}/${sysconfdir}/${BPN}/static
    ln -s ${datadir}/sftpgo/templates ${D}/${sysconfdir}/${BPN}/templates
  
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
