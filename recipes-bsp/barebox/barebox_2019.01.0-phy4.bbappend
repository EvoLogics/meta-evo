FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-enabled-fec2.patch \
            file://0001-Changed-hostname.patch \
            "


#Currently there is no rauc support for eMMC
python do_env_append_mx6ul(){

    env_add(d, "nv/dev.eth1.mode", "static")
    env_add(d, "nv/dev.eth1.ipaddr", "10.0.0.2")
    env_add(d, "nv/dev.eth1.netmask", "255.255.255.0")
    env_add(d, "nv/net.gateway", "10.0.0.1")
    env_add(d, "nv/dev.eth1.serverip", "10.0.0.101")
    env_add(d, "nv/dev.eth1.linux.devname", "eth1")
    env_add(d, "nv/dhcp.vendor_id", "evologics")

    env_add(d, "nv/dev.eth0.mode", "static")
    env_add(d, "nv/dev.eth0.ipaddr", "10.0.0.100")
    env_add(d, "nv/dev.eth0.netmask", "255.255.255.0")
    env_add(d, "nv/net.gateway", "10.0.0.1")
    env_add(d, "nv/dev.eth0.serverip", "10.0.0.101")
    env_add(d, "nv/dev.eth0.linux.devname", "eth0")
    env_add(d, "nv/dhcp.vendor_id", "evologics")
    env_add(d, "nv/autoboot_timeout", "1")


    env_add(d, "bin/image_update_eth",
    """#!/bin/sh
    echo "Flashing sd image from ethernet"
    detect mmc1
    ifup eth1
    cp -v /mnt/tftp/core-image-minimal-mx6ul-comm-module.sdcard /dev/mmc1
    """)

    env_add(d, "bin/barebox_update_eth",
    """#!/bin/sh
    echo "Flashing barebox from ethernet"
    detect mmc1
    ifup eth1
    barebox_update -t mmc1 /mnt/tftp/barebox.bin
    """)

    env_rm(d, "boot/system0")
    env_rm(d, "boot/system1")
    env_rm(d, "nv/bootchooser.targets")
    env_rm(d, "nv/bootchooser.system0.boot")
    env_rm(d, "nv/bootchooser.system1.boot")
    env_rm(d, "nv/bootchooser.state_prefix")
    env_rm(d, "bin/rauc_flash_nand_from_mmc")
    env_rm(d, "bin/rauc_flash_nand_from_tftp")
}

COMPATIBLE_MACHINE += "|mx6ul-comm-module"
