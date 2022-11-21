SUMMARY = "HAProxy load balancer"
DESCRIPTION = "HAProxy is a free, very fast and reliable solution offering high availability, load balancing, and proxying for TCP and HTTP-based applications."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d862e836f92129cdc0ecccc54eed5e0"

DEPENDS = "libpcre openssl zlib"
RDEPENDS_${PN} = "openssl"

SRC_URI = "http://www.haproxy.org/download/2.6/src/haproxy-2.6.6.tar.gz \
           file://haproxy.cfg \
           file://haproxy.init \
           file://haproxy.service \
          "

SRC_URI[md5sum] = "09de5e3ad5a4be36ce6398ce37ce801e"
SRC_URI[sha256sum] = "d0c80c90c04ae79598b58b9749d53787f00f7b515175e7d8203f2796e6a6594d"

inherit systemd useradd

#create a user for running haproxy
HAP_USER_HOME = "/home/haproxy"
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --create-home --home ${HAP_USER_HOME} --shell /bin/false --groups haproxy --gid haproxy haproxy"
GROUPADD_PARAM_${PN} = "haproxy"

EXTRA_OEMAKE = "'CPU=generic' \
                'TARGET=linux-${TCLIBC}' \
                'USE_GETADDRINFO=1' \
                'USE_OPENSSL=1' \
                'USE_PCRE=1' 'USE_PCRE_JIT=1' \
                'USE_ZLIB=1' \
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

FILES_${PN} = "${sbindir} \
               ${sysconfdir} \
               ${systemd_unitdir} \
              "

SYSTEMD_SERVICE_${PN} = "haproxy.service"
