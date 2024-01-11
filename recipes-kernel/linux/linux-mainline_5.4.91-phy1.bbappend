FILESEXTRAPATHS:prepend:mx6ul-comm-module := "${THISDIR}/linux-phytec-common:"

SRC_URI:append:mx6ul-comm-module = " 		                \
    file://${MACHINE}.cfg                                   \
    file://imx6ul-comm-module.dtsi 			                \
    file://imx6ul-comm-module.dts 			                \
"


do_configure:prepend:mx6ul-comm-module() {
	cp ${WORKDIR}/*.dtsi ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/*.dts ${S}/arch/arm/boot/dts/
}

COMPATIBLE_MACHINE .= "|mx6ul-comm-module"

