FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-enabled-fec2.patch \
            file://0001-Changed-hostname.patch \
            file://0001-add-state-for-2-boot-partitions.patch \
            "


#Currently there is no rauc support for eMMC
python do_env_append_mx6ul(){

    env_rm(d, "boot/system0")
    env_rm(d, "boot/system1")
    env_rm(d, "nv/bootchooser.targets")
    env_rm(d, "nv/bootchooser.system0.boot")
    env_rm(d, "nv/bootchooser.system1.boot")
    env_rm(d, "nv/bootchooser.state_prefix")
    env_rm(d, "bin/rauc_flash_nand_from_mmc")
    env_rm(d, "bin/rauc_flash_nand_from_tftp")
    env_rm(d, "boot/emmc")
    env_rm(d, "init/automount")

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

#Initialize State to Default
/env/bin/set_default_state
    """)

    env_add(d, "bin/barebox_update_eth",
    """#!/bin/sh
echo "Flashing barebox from ethernet"
detect mmc1
ifup eth1
barebox_update -t mmc1 /mnt/tftp/barebox.bin
    """)

    env_add(d, "boot/system0",
    """#!/bin/sh
[ -e /env/config-expansions ] && /env/config-expansions

global.bootm.image="/mnt/kernel0/zImage"
global.bootm.oftree="/mnt/kernel0/oftree"
global.linux.bootargs.dyn.root="root=/dev/mmcblk1p2 rootflags='discard,data=journal'
    """)

    env_add(d, "boot/system1",
    """#!/bin/sh
[ -e /env/config-expansions ] && /env/config-expansions

global.bootm.image="/mnt/kernel1/zImage"
global.bootm.oftree="/mnt/kernel1/oftree"
global.linux.bootargs.dyn.root="root=/dev/mmcblk1p4 rootflags='discard,data=journal'
    """)


    env_add(d, "init/automount", 
"""#!/bin/sh

# automount tftp server based on $eth0.serverip

mkdir -p /mnt/tftp
automount /mnt/tftp 'ifup eth0 && mount -t tftp $eth0.serverip /mnt/tftp'

mkdir -p /mnt/mmc
automount -d /mnt/mmc 'mmc0.probe=1 && [ -e /dev/mmc0.0 ] && mount /dev/mmc0.0 /mnt/mmc'

if [ -e /dev/mmc1 ]; then
    mkdir -p /mnt/kernel0
    automount -d /mnt/kernel0 'mmc1.probe=1 && [ -e /dev/mmc1.0 ] && mount /dev/mmc1.0 /mnt/kernel0'
fi

if [ -e /dev/mmc1 ]; then
    mkdir -p /mnt/kernel1
    automount -d /mnt/kernel1 'mmc1.probe=1 && [ -e /dev/mmc1.2 ] && mount /dev/mmc1.2 /mnt/kernel1'
fi
    """)

    env_add(d, "bin/set_default_state",
    """#!/bin/sh

#Set Defaults
state.bootstate.system0.priority=21
state.bootstate.system0.remaining_attempts=10
state.bootstate.system0.ok=0
state.bootstate.system1.priority=20
state.bootstate.system1.remaining_attempts=10
state.bootstate.system1.ok=0

#Save to EEPROM
state -s

    """)

    env_add(d, "nv/bootchooser.targets", """system0 system1""")
    env_add(d, "nv/bootchooser.system0.boot", """system0""")
    env_add(d, "nv/bootchooser.system1.boot", """system1""")
    env_add(d, "nv/bootchooser.state_prefix", """state.bootstate""")
    env_add(d, "nv/boot.default", """bootchooser system0""")
}

do_configure_append_mx6ul() {
    # Don't compile target tools for barebox here
    kconfig_set CONFIG_STATE y
    kconfig_set CONFIG_STATE_DRV y
    kconfig_set CONFIG_CMD_STATE y
}

COMPATIBLE_MACHINE += "|mx6ul-comm-module"
