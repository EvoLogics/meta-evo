FILES:lib${BPN}:sama5d2-roadrunner-evo = "${libdir}/libexpect${PV}.so \
                   ${libdir}/expect${PV}/pkgIndex.tcl"

FILES:${PN}:sama5d2-roadrunner-evo = "${bindir}/expect"
RDEPENDS:${PN}:sama5d2-roadrunner-evo += "lib${BPN}"

FILES:${PN}-scripts:sama5d2-roadrunner-evo  = "\
    ${libdir}/expect${PV} \
    ${bindir}/* \
"

# NOTE: lib${BPN} before ${PN}-script becouse lib${BPN}
# match pkgIndex.tcl, and ${PN}-scripts match all other files
# NOTE: += will append to variable, after ${PN}-bin
PACKAGES:sama5d2-roadrunner-evo  += "lib${BPN} ${PN}-scripts"
