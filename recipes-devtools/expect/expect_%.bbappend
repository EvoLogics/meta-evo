FILES:lib${BPN} = "${libdir}/libexpect${PV}.so \
                   ${libdir}/expect${PV}/pkgIndex.tcl"

FILES:${PN} = "${bindir}/expect"
RDEPENDS:${PN} += "lib${BPN}"

FILES:${PN}-scripts  = "\
    ${libdir}/expect${PV} \
    ${bindir}/* \
"

# NOTE: lib${BPN} before ${PN}-script becouse lib${BPN}
# match pkgIndex.tcl, and ${PN}-scripts match all other files
# NOTE: += will append to variable, after ${PN}-bin
PACKAGES  += "lib${BPN} ${PN}-scripts"
