SUMMARY = "HAProxy load balancer"
DESCRIPTION = "HAProxy is a free, very fast and reliable solution offering high availability, load balancing, and proxying for TCP and HTTP-based applications."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d862e836f92129cdc0ecccc54eed5e0"

DEPENDS = "libpcre openssl zlib"

SRC_URI = "http://www.haproxy.org/download/1.6/src/haproxy-1.6.0.tar.gz \
           file://haproxy.cfg \
           file://haproxy.service \
           file://haproxy_gencert.sh \
          "

SRC_URI[md5sum] = "2361c7dd0a709a1b235215225875f34c"
SRC_URI[sha256sum] = "e83a272b7d3638cf1d37bba58d3e75f497c1862315ee5bb7f5efc1d98d26e25b"

inherit systemd useradd

#create a user for running haproxy
HAP_USER_HOME = "/etc/haproxy"
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --home ${HAP_USER_HOME} --no-create-home --shell /bin/false --groups haproxy --gid haproxy haproxy"
GROUPADD_PARAM_${PN} = "haproxy"

do_configure() {
    cd ${S}
    sed -i 's:/usr/sbin/haproxy:/usr/bin/haproxy:' src/haproxy-systemd-wrapper.c
}

EXTRA_OEMAKE = "'CPU=generic' \
                'TARGET=linux2628' \
                'USE_GETADDRINFO=1' \
                'USE_OPENSSL=1' \
                'USE_PCRE=1' 'USE_PCRE_JIT=1' \
                'USE_ZLIB=1' \
               "

do_compile() {
    cd ${S}
    oe_runmake CC="${CC}" CFLAGS="${CFLAGS}" SBINDIR="${bindir}" \
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
    #only install binaries, docs are not usefull and fail to install anyway (missing file error in source package)
    oe_runmake install-bin \
               PREFIX="${prefix}" \
               SBINDIR="${bindir}" \
               DESTDIR=${D} \
               INCLUDEDIR=${includedir}

    install -D -m 0644 ${WORKDIR}/haproxy.service ${D}${systemd_unitdir}/system/haproxy.service
    install -D -m 0755 ${WORKDIR}/haproxy_gencert.sh ${D}${sbindir}/haproxy_gencert.sh
    install -D -m 0644 ${WORKDIR}/haproxy.cfg ${D}${sysconfdir}/haproxy/haproxy.cfg

    #install ssl folder for certificate
    install -m 700 -d ${D}/${sysconfdir}/ssl/haproxy
    chown haproxy:haproxy ${D}/${sysconfdir}/ssl/haproxy
}

FILES_${PN} = "${bindir} \
               ${sysconfdir} \
               ${sbindir} \
               ${systemd_unitdir} \
               ${sysconfdir}/ssl/haproxy \
              "

SYSTEMD_SERVICE_${PN} = "haproxy.service"
