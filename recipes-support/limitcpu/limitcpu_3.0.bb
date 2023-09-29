SUMMARY = "LimitCPU is a program to throttle the CPU cycles used by other applications. \
LimitCPU will monitor a process and make sure its CPU usage stays at or below a given percentage. \
This can be used to make sure your system has plenty of CPU cycles available for other tasks. \
It can also be used to keep laptops cool in the face of CPU-hungry processes and for limiting virtual machines. \
"

HOMEPAGE = "http://limitcpu.sourceforge.net"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "${SOURCEFORGE_MIRROR}/limitcpu/cpulimit-${PV}.tar.gz \
          "

SRC_URI[md5sum] = "bdfae460475241d6253a74abf4dffbad"
SRC_URI[sha256sum] = "ad2f415eb2bbda3e83a8a2d918ef5e90f52ebcc6fee61e94bf917b3e84ebb49c"

S = "${WORKDIR}/cpulimit-${PV}"

do_install() {
	install -d ${D}${sbindir}
  install -m 755 ${S}/cpulimit ${D}${sbindir}/cpulimit
}
