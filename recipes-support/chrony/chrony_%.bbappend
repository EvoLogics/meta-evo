FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
      file://chrony.conf \
      "

do_configure_append() {
    # getrandom() slowdown bootup run of chrony to 2 min.
    # Force to use embedded UTI_GetRandomBytesUrandom() which use /dev/urandom
    sed -i '/HAVE_GETRANDOM/d' config.h
}
