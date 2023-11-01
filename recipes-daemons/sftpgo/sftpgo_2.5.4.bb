SUMMARY = "SFTPGo"
DESCRIPTION = "Fully featured and highly configurable SFTP server with optional HTTP/S, FTP/S and WebDAV support."
GO_IMPORT = "github.com/drakkan/sftpgo"

HOMEPAGE = "https://sftpgo.com/"
LICENSE = "AGPL-3.0"
#LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=0d08d799c3aa0d044526e77b67c5eebc"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/AGPL-3.0;md5=73f1eb20517c55bf9493b7dd6e480788"
SRC_URI = " \
  file://sftpgo.json \
  "

#git://${GO_IMPORT};branch=${SRCBRANCH};protocol=https

SRCREV = "66c14bebd8fb5d19201a74d74a93f3c45a783bb3"
SRCBRANCH = "2.3.x"

# dunfell go is too old to compile...

#GO_INSTALL = "${GO_IMPORT}"

#inherit go-mod

#GOBUILDFLAGS:append = " -tags nogcs,nos3,noazblob,nobolt,nomysql,nopgsql,nometrics,unixcrypt "

do_configure() {
  :
}

do_compile() {
  :
}

do_install() {
  install -d ${D}/${sysconfdir}/sftpgo
    install -m 644 ${WORKDIR}/sftpgo.json ${D}/${sysconfdir}/sftpgo
}
