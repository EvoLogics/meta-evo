SUMMARY = "linux containers"
DESCRIPTION = "lxc aims to use these new functionnalities to provide an userspace container object"
SECTION = "console/utils"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE.GPL2;md5=751419260aa954499f7abaabaa882bbe \
                    file://LICENSE.LGPL2.1;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING;md5=b0a361b48fc45b3b55d64016ed018d97"

SRC_URI = "https://linuxcontainers.org/downloads/lxc/lxc-${PV}.tar.gz"
SRC_URI[md5sum] = "365fcca985038910e19a1e0fff15ed07"
SRC_URI[sha256sum] = "1fcf0610e9140eceb4be2334eb537bb9c5a213faea77c793ab3c62b86f37e52b"

# NOTE: the following prog dependencies are unknown, ignoring: doxygen
# NOTE: unable to map the following pkg-config dependencies: dlog libseccomp
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: apparmor seccomp pam util pthread selinux
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "openssl libcap"

inherit autotools pkgconfig lib_package

ON="${@'${BP}'.replace('lib','')}"
S = "${WORKDIR}/${ON}"

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = "--enable-tools     \
                --enable-commands  \
                --without-distro   \
                --disable-bash     \
                --disable-tests    \
                --disable-examples \
               "

RCONFLICTS_${PN} = "lxc"

do_install_append() {
    rm -rf ${D}${nonarch_libdir}/lxc/rootfs/README
}

# Remove binary files from ${PN} and ${PN}-bin,
# becouse binaryes will splited to separated packages.
FILES_${PN}_remove     = "${bindir}/*"
FILES_${PN}-bin_remove = "${bindir}/*"

# TODO: what -bin depends from libexec?
FILES_${PN}-libexec         = "${libexecdir}/*"

FILES_${PN}-conf            = "${sysconfdir}/*"

# Cannot use do_split_packages() becouse legitimize_package_name()
# not replace '.' with '-', produce package name like 'liblxc-bin-init.lxc'
FILES_${PN}-bin-init-lxc    = "${sbindir}/init.lxc*"
RDEPENDS_${PN}-bin-init-lxc = "${PN}-common"

# lxc-execute run init.lxc*
RDEPENDS_${PN}-bin-lxc-execute = "${PN}-bin-init-lxc"

FILES_${PN}-common          = "  \
    ${localstatedir}/lib/lxc     \
    ${localstatedir}/cache       \
    ${nonarch_libdir}/lxc/rootfs \
"

FILES_${PN}-extra           = "\
    ${datadir}/lxc/lxc-patch.py  \
    ${datadir}/lxc/lxc.functions \
    ${datadir}/lxc/hooks/*       \
    ${datadir}/lxc/selinux/*     \
    ${datadir}/lxc/templates/*   \
    ${datadir}/lxc/config/*      \
"
RDEPENDS_${PN}-extra = "bash"

PACKAGES  =+ " ${PN}-bin-init-lxc ${PN}-common ${PN}-libexec ${PN}-conf ${PN}-extra"
PACKAGES_DYNAMIC = '${PN}-bin-.*'

# *-bin package will be empty with depends from all *-bin-* packages
ALLOW_EMPTY_${PN}-bin = "1"

python populate_packages_prepend() {
    import glob
    pn = d.getVar('PN') or ''
    bindir = d.expand('${bindir}')
    pkgs = do_split_packages(d, bindir, '(lxc-.*)', '${BPN}-bin-%s', 'Binary in %s', extra_depends='${BPN}-common')

    d.prependVar("RDEPENDS_"  + pn + '-bin', "%s " % (" ".join(pkgs)))
}
