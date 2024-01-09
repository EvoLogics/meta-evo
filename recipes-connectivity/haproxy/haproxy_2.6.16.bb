SUMMARY = "HAProxy load balancer"
DESCRIPTION = "HAProxy is a free, very fast and reliable solution offering high availability, load balancing, and proxying for TCP and HTTP-based applications."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d862e836f92129cdc0ecccc54eed5e0"

DEPENDS = "libpcre openssl zlib  ${@bb.utils.contains("DISTRO_FEATURES","systemd",'systemd','',d)}"
RDEPENDS:${PN} = "openssl"

SRC_URI = "http://www.haproxy.org/download/2.6/src/${PN}-${PV}.tar.gz \
           file://haproxy.cfg \
           file://haproxy.init \
           file://haproxy.service \
          "

SRC_URI[md5sum] = "b01e605cdaf2742fcedf214a61e187b4"
SRC_URI[sha256sum] = "faac6f9564caf6e106fe22c77a1fb35406afc8cd484c35c2c844aaf0d7a097fb"

inherit systemd useradd

#create a user for running haproxy
HAP_USER_HOME = "/home/haproxy"
USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system --create-home --home ${HAP_USER_HOME} --shell /bin/false --groups haproxy --gid haproxy haproxy"
GROUPADD_PARAM:${PN} = "haproxy"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = "'CPU=generic' \
                'TARGET=linux-${TCLIBC}' \
                'USE_GETADDRINFO=1' \
                'USE_OPENSSL=1' \
                'USE_PCRE=1' 'USE_PCRE_JIT=1' \
                'USE_ZLIB=1' \
                ${@bb.utils.contains("DISTRO_FEATURES","systemd",'USE_SYSTEMD=1','',d)} \
               "

do_compile() {
    cd ${S}
    oe_runmake CC="${CC}" CPU_CFLAGS="${CFLAGS}" SBINDIR="${sbindir}" \
               PREFIX="${prefix}" \
               ZLIB_INC=${STAGING_INCDIR} \
               ZLIB_LIB=${STAGING_LIBDIR} \
               PCRE_INC=${STAGING_INCDIR} \
               PCRE_LIB=${STAGING_LIBDIR} \
               SSL_INC=${STAGING_INCDIR} \
               SSL_LIB=${STAGING_LIBDIR}
}

do_install() {
  cd ${S}
  # only install binaries
  oe_runmake install-bin \
    PREFIX="${prefix}" \
    SBINDIR="${sbindir}" \
    DESTDIR=${D} \
    INCLUDEDIR=${includedir}

  install -D -m 0644 ${WORKDIR}/haproxy.cfg ${D}${sysconfdir}/haproxy/haproxy.cfg

  if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
    install -d ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/haproxy.service ${D}${systemd_system_unitdir}/
  else
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/haproxy.init ${D}${sysconfdir}/init.d/haproxy
  fi
}

FILES:${PN} = "${sbindir} \
               ${sysconfdir} \
               ${systemd_unitdir} \
              "

SYSTEMD_SERVICE:${PN} = "haproxy.service"
