FILESEXTRAPATHS_prepend := "${THISDIR}/linux-common:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/linux-phytec-common:"

SRC_URI_append_mx6ul-comm-module += " 	\
    file://commod_defconfig 			\
    file://imx6ul-comm-module.dtsi 		\
    file://imx6ul-comm-module.dts 		\
"


do_configure_prepend_mx6ul-comm-module() {
	cp ${WORKDIR}/*.dtsi ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/*.dts ${S}/arch/arm/boot/dts/
    cp ${WORKDIR}/commod_defconfig ${S}/arch/arm/configs/imx_v6_v7_defconfig
}

COMPATIBLE_MACHINE .= "|mx6ul-comm-module"

