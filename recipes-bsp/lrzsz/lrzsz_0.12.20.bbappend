FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:libc-musl = "file://0001-Fix-rx-hang-with-musl.patch"

