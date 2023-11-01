FILESEXTRAPATHS:prepend:mio-2363 := "${THISDIR}/${PN}:"

do_configure:append() {
    CFG="$(ls ${WORKDIR}/*.cfg 2> /dev/null || true)"
    test -n "$CFG" && cat ${WORKDIR}/*.cfg >> ${B}/.config || true
}
