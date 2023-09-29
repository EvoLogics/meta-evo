SUMMARY = "Metapackage to build everything for roadrunner"
DESCRIPTION = "Metapackage to build everything for roadrunner"
AUTHOR = "Maksym Komar <komar@evologics.de>"
LICENSE = "MIT"

SECTION = "meta"

inherit packagegroup

RPACKAGES = "linux-at91 \
    evologics-base-image \
    swupdate-images-evo \
    meta-toolchain \
    packagegroup-erlang-embedded \
"

do_build[depends] += "evologics-base-image:do_populate_sdk"
do_build[depends] += "evologics-base-image:do_populate_sdk_ext"

