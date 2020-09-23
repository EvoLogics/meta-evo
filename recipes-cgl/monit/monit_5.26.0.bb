SUMMARY = "Monit is a tool used for system monitoring and error recovery"
DESCRIPTION = "Monit is a free open source utility for managing and monitoring, \
  processes, programs, files, directories and filesystems on a UNIX system. \
  Monit conducts automatic maintenance and repair and can execute meaningful \
  causal actions in error situations. \
  "
HOMEPAGE = "http://mmonit.com/monit/"

FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"

LICENSE = "AGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea116a7defaf0e93b3bb73b2a34a3f51"

DEPENDS = "openssl zlib virtual/crypt"

SRC_URI = "\
	http://mmonit.com/monit/dist/${BP}.tar.gz \
	file://init \
	"

SRC_URI_append_mx6ul-comm-module = "    \
		file://start-monit.sh    \
		file://monit.service  \
"

SRC_URI[md5sum] = "9f7dc65e902c103e4c5891354994c3df"
SRC_URI[sha256sum] = "87fc4568a3af9a2be89040efb169e3a2e47b262f99e78d5ddde99dd89f02f3c2"

INITSCRIPT_NAME = "monit"
INITSCRIPT_PARAMS = "defaults 99"

inherit autotools-brokensep update-rc.d systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "monit.service"
SYSTEMD_AUTO_ENABLE = "enable"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'pam', d)}"
PACKAGECONFIG[pam] = "--with-pam,--without-pam,libpam"

EXTRA_OECONF = "\
	libmonit_cv_setjmp_available=no \
	libmonit_cv_vsnprintf_c99_conformant=no \
	--with-ssl-lib-dir=${STAGING_LIBDIR} \
	--with-ssl-incl-dir=${STAGING_INCDIR} \
	"

do_configure_prepend() {
    rm -rf ${S}/m4
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/monit

	install -m 600 ${S}/monitrc ${D}${sysconfdir}/monitrc
	install -m 700 -d ${D}${sysconfdir}/monit.d/
	sed -i -e 's:#*\s*include /etc/monit.d/:include ${sysconfdir}/monit.d/:' \
         -e 's:\s*use address localhost:# use address localhost:' \
	       ${D}${sysconfdir}/monitrc

  install -m 600 ${D}${sysconfdir}/monitrc ${D}${sysconfdir}/monitrc-ro-rootfs
	sed -i -e 's:#*\s*set idfile.*:set idfile /etc/monitid:' \
         -e 's:#*\s*set pidfile.*:set pidfile /var/run/monit.pid:' \
         -e 's:#*\s*set statefile.*:set statefile /var/tmp/monit.state:' \
	       ${D}${sysconfdir}/monitrc-ro-rootfs
}

do_install_append_mx6ul-comm-module(){
	install -D -m 0644 ${WORKDIR}/monit.service ${D}${systemd_system_unitdir}/monit.service
	install -d ${D}${base_sbindir}
	install -m 0755 ${WORKDIR}/start-monit.sh ${D}${base_sbindir}/
}


CONFFILES_${PN} += "${sysconfdir}/monitrc"
