require cryptsetup-plain.inc

do_compile () {
    ${CC} -o ${PN} cryptsetup-plain.c \
         -static -lcryptsetup -lpopt -ljson-c -luuid -ldevmapper 
}
