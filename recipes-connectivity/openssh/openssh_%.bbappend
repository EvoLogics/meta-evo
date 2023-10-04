# Becouse openssh-sftp-server-dev installing,
# fixing a conflict dropbear-dev vs openssh-dev installing in image
# https://patchwork.openembedded.org/patch/154992/

PACKAGES =+ "${PN}-sftp-server-dev"
RDEPENDS:${PN}-sftp-server-dev += "${PN}-sftp-server"

