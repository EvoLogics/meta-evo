require cryptsetup-plain.inc

do_compile () {
    ## lib libdevmapper dynamically. then libdevmapper depend from musl.so (+500K)
    #${CC} ${LDFLAGS} -o cryptsetup-plain cryptsetup-plain.c \
    #     -Wl,-Bstatic -lcryptsetup -lpopt -ljson-c -luuid -Wl,-Bdynamic -ldevmapper

    # build statically only libcryptsetup, libpopt and libdevmapper
    ${CC} ${LDFLAGS} -o ${PN} cryptsetup-plain.c \
         -Wl,-Bstatic -lcryptsetup -lpopt -ldevmapper -Wl,-Bdynamic -lc -ljson-c -luuid
}
