SUMMARY = "Protocol Buffers - structured data serialisation mechanism"
DESCRIPTION = "Protocol Buffers are a way of encoding structured data in an \
efficient yet extensible format. Google uses Protocol Buffers for almost \
all of its internal RPC protocols and file formats."
HOMEPAGE = "https://github.com/google/protobuf"
SECTION = "console/tools"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37b5762e07f0af8c74ce80a8bda4266b"

DEPENDS = "zlib"
DEPENDS_append_class-target = " protobuf-native"

SRCREV = "f0dc78d7e6e331b8c6bb2d5283e06aa26883ca7c"

SRC_URI = "git://github.com/protocolbuffers/protobuf.git;branch=21.x;protocol=https \
           file://run-ptest \
           file://0001-examples-Makefile-respect-CXX-LDFLAGS-variables-fix-.patch \
           file://0001-Fix-linking-error-with-ld-gold.patch \
           "
SRC_URI_append_mips_toolchain-clang = " file://0001-Fix-build-on-mips-clang.patch "
SRC_URI_append_mipsel_toolchain-clang = " file://0001-Fix-build-on-mips-clang.patch "

S = "${WORKDIR}/git"

inherit cmake pkgconfig ptest

PACKAGECONFIG ??= ""
PACKAGECONFIG_class-native ?= "compiler"
PACKAGECONFIG_class-nativesdk ?= "compiler"
PACKAGECONFIG[python] = ",,"
PACKAGECONFIG[compiler] = "-Dprotobuf_BUILD_PROTOC_BINARIES=ON,-Dprotobuf_BUILD_PROTOC_BINARIES=OFF"

EXTRA_OECMAKE += "\
    -Dprotobuf_BUILD_SHARED_LIBS=ON \
    -Dprotobuf_BUILD_LIBPROTOC=ON \
    -Dprotobuf_BUILD_TESTS=OFF \
    -Dprotobuf_BUILD_EXAMPLES=OFF \
"

TEST_SRC_DIR = "examples"
LANG_SUPPORT = "cpp ${@bb.utils.contains('PACKAGECONFIG', 'python', 'python', '', d)}"

do_compile_ptest() {
	mkdir -p "${B}/${TEST_SRC_DIR}"

	# Add the location of the cross-compiled header and library files
	# which haven't been installed yet.
	cp "${B}/protobuf.pc" "${B}/${TEST_SRC_DIR}/protobuf.pc"
	cp ${S}/${TEST_SRC_DIR}/*.cc "${B}/${TEST_SRC_DIR}/"
	cp ${S}/${TEST_SRC_DIR}/*.proto "${B}/${TEST_SRC_DIR}/"
	cp ${S}/${TEST_SRC_DIR}/*.py "${B}/${TEST_SRC_DIR}/"
	cp ${S}/${TEST_SRC_DIR}/Makefile "${B}/${TEST_SRC_DIR}/"
	sed -e 's|libdir=|libdir=${PKG_CONFIG_SYSROOT_DIR}|' -i "${B}/${TEST_SRC_DIR}/protobuf.pc"
	sed -e 's|Cflags:|Cflags: -I${S}/src|' -i "${B}/${TEST_SRC_DIR}/protobuf.pc"
	sed -e 's|Libs:|Libs: -L${B}|' -i "${B}/${TEST_SRC_DIR}/protobuf.pc"
	# Until out-of-tree build of examples is supported, we have to use this approach
	sed -e 's|../src/google/protobuf/.libs/timestamp.pb.o|${B}/CMakeFiles/libprotobuf.dir/src/google/protobuf/timestamp.pb.cc.o|' -i "${B}/${TEST_SRC_DIR}/Makefile"
	export PKG_CONFIG_PATH="${B}/${TEST_SRC_DIR}"

	# Save the pkgcfg sysroot variable, and update it to nothing so
	# that it doesn't append the sysroot to the beginning of paths.
	# The header and library files aren't installed to the target
	# system yet.  So the absolute paths were specified above.
	save_pkg_config_sysroot_dir=$PKG_CONFIG_SYSROOT_DIR
	export PKG_CONFIG_SYSROOT_DIR=

	# Compile the tests
	for lang in ${LANG_SUPPORT}; do
		oe_runmake -C "${B}/${TEST_SRC_DIR}" ${lang}
	done

	# Restore the pkgconfig sysroot variable
	export PKG_CONFIG_SYSROOT_DIR=$save_pkg_config_sysroot_dir
}

do_install_ptest() {
	local olddir=`pwd`

	cd "${S}/${TEST_SRC_DIR}"
	install -d "${D}/${PTEST_PATH}"
	for i in add_person* list_people*; do
		if [ -x "$i" ]; then
			install "$i" "${D}/${PTEST_PATH}"
		fi
	done
	cp "${B}/${TEST_SRC_DIR}/addressbook_pb2.py" "${D}/${PTEST_PATH}"
	cd "$olddir"
}

PACKAGE_BEFORE_PN = "${PN}-compiler ${PN}-lite"

FILES_${PN}-compiler = "${bindir} ${libdir}/libprotoc${SOLIBS}"
FILES_${PN}-lite = "${libdir}/libprotobuf-lite${SOLIBS}"

SYSROOT_DIRS += "${bindir}"

RDEPENDS_${PN}-compiler = "${PN}"
RDEPENDS_${PN}-dev += "${PN}-compiler"
RDEPENDS_${PN}-ptest = "bash ${@bb.utils.contains('PACKAGECONFIG', 'python', 'python3-protobuf', '', d)}"

MIPS_INSTRUCTION_SET = "mips"

BBCLASSEXTEND = "native nativesdk"

LDFLAGS_append_arm = " -latomic"
LDFLAGS_append_mips = " -latomic"
LDFLAGS_append_powerpc = " -latomic"
LDFLAGS_append_mipsel = " -latomic"
