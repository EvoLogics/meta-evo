
inherit update-alternatives

ALTERNATIVE:${PN} = "kermit"
ALTERNATIVE_LINK_NAME[kermit] = "${bindir}/kermit"
ALTERNATIVE_TARGET[kermit] = "${bindir}/ckermit"
ALTERNATIVE_PRIORITY = "100"

do_install:append () {
    mv ${D}${BINDIR}/kermit ${D}${BINDIR}/ckermit 
    # Fix link, point to ckermit.
    # Also fix wrong "kermit-sshusb" instead "kermit-sshsub"
    rm -f ${D}${BINDIR}/kermit-ssh*
    (cd ${D}${BINDIR} && ln -s ${BINDIR}/ckermit kermit-sshsub)
}
