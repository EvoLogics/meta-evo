FILESEXTRAPATHS:prepend_mx6ul-comm-module := "${THISDIR}/linux-phytec-common:"

SRC_URI:append_mx6ul-comm-module = " 		                \
    file://${MACHINE}.cfg                                   \
    file://imx6ul-comm-module.dtsi 			                \
    file://imx6ul-comm-module.dts 			                \
    file://0001-Added-Kernel-drivers-for-VEML6030.patch     \ 
    file://0001-fix-sdma-event-bug-applicable-for-uart6-on-imx-contr.patch     \
"


do_configure:prepend_mx6ul-comm-module() {
	cp ${WORKDIR}/*.dtsi ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/*.dts ${S}/arch/arm/boot/dts/
}

COMPATIBLE_MACHINE .= "|mx6ul-comm-module"

