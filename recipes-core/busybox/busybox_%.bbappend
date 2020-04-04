# http://www.yoctoproject.org/docs/current/kernel-dev/kernel-dev.html#changing-the-configuration
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
FILESEXTRAPATHS_prepend_mx6ul-comm-module := "${THISDIR}/commod-mx6ul:"

SRC_URI_sama5d2-roadrunner-evo += " \
    file://coreutils.cfg \
    file://brctl.cfg \
    file://compress.cfg \
    file://cron.cfg \
    file://date.cfg \
    file://dd.cfg \
    file://dhcp.cfg \
    file://find-xargs.cfg \
    file://flash.cfg \
    file://ftp.cfg \
    file://httpd.cfg \
    file://i2c.cfg \
    file://inetd.cfg \
    file://ip-utils.cfg \
    file://less.cfg \
    file://misc-utils.cfg \
    file://nc.cfg \
    file://net-misc.cfg \
    file://ntp.cfg \
    file://proc.cfg \
    file://sh.cfg \
    file://telnetd.cfg \
    file://tftpd.cfg \
    file://tun.cfg \
    file://ubi.cfg \
    file://vi.cfg \
    file://modules.cfg \
    file://console.cfg \
    file://new0.cfg \
"

SRC_URI_append_mx6ul-comm-module = "    \
    file://defconfig                    \
" 
