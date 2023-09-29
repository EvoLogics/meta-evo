FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_libc-musl = "file://0001-Fix-rx-hang-with-musl.patch"

