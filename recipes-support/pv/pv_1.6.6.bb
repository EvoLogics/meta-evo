DESCRIPTION = "pv - Pipe Viewer - is a terminal-based tool for monitoring the progress of data through a pipeline."

LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://doc/COPYING;md5=9c50db2589ee3ef10a9b7b2e50ce1d02"

SRC_URI = "http://www.ivarch.com/programs/sources/pv-${PV}.tar.bz2"
SRC_URI[md5sum] = "ff3564fddcc2b9bd4a9c1d143aba4b4c"
SRC_URI[sha256sum] = "608ef935f7a377e1439c181c4fc188d247da10d51a19ef79bcdee5043b0973f1"

inherit autotools

## broken autotools
#do_configure() {
#	# cp ${S}/autoconf/configure.in ${S}
#	# gnu-configize --force
#	oe_runconf
#}
