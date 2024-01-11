SUMMARY = "Homer - static homepage"
DESCRIPTION = "A dead simple static HOMepage for your servER to keep your services on hand, from a simple yaml configuration file."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI = " \
  https://github.com/bastienwirtz/homer/releases/download/v${PV}/${PN}.zip;subdir=prebuilt \
  "

SRC_URI[sha256sum] = "779b5d30464318cf78d6139fa30175c8d4b1cac0436bc61e0f1c212db7e17744"

FILES:${PN} = "/opt/"

do_configure() {
  :
}

do_compile() {
  :
}

do_install(){
  install -d ${D}/opt/${BPN}
    cp ${WORKDIR}/prebuilt/index.html ${D}/opt/${BPN}
    cp ${WORKDIR}/prebuilt/*.js ${D}/opt/${BPN}
    cp -r ${WORKDIR}/prebuilt/resources ${D}/opt/${BPN}
    install -d 755 -d ${D}/opt/${BPN}/assets
}
