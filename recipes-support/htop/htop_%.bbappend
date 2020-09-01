PACKAGECONFIG = "proc cgroup taskstats linux-affinity"

do_install () {
    oe_runmake PREFIX=${prefix} DESTDIR=${D} install-exec
}
