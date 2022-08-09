#!/bin/sh

mnt=

cleanup() {
    [ -n "$mnt" ] || return
    # Unmount all directoryes in temporary directory
    for d in sys proc dev run; do
	if mountpoint -q "${mnt}/${d}"; then
	    umount "${mnt}/${d}" >/dev/null 2>&1 || true
	fi
    done
    # Unmount the new rootfs
    if mountpoint -q "$mnt"; then
	umount "$mnt" > /dev/null 2>&1 || true
    fi
    # Delete the temporary directory
    rmdir "$mnt" >/dev/null 2>&1 || true
}

# Check if argument 1 is empty. Device Name is needed 
if [ -z "$1" ]; then
    echo "ERR: missing device name" >&2
    # Exit with error
    exit 1
fi

echo "Installing NVIDIA bootloader update payload"

# Make temporary directory to mount new intalled rootfs
mnt=$(mktemp -d -t nvbup.XXXXXX)
# Check if error was directory cant be created for some aparent reason.
if [ -z "$mnt" -o ! -d "$mnt" ]; then
    echo "ERR: could not create directory for mounting install partition" >&2
    # Exit with error
    exit 1
fi
# Mount the newly installed rootfs into the temporary directory
if ! mount -o ro "$1" "$mnt"; then
    echo "ERR: could not mount $1 for bootloader update" >&2
    # Cleanup the mounted directory and remove the temporary directory
    cleanup
fi

# Mount temporarily the required directories from new rootfs
mount --bind /sys "${mnt}/sys"
mount --bind /proc "${mnt}/proc"
mount --bind /dev "${mnt}/dev"
mount --bind /run "${mnt}/run"

# Use the directories from new rootfs as aparrent root to perform the binary updates
if ! chroot "${mnt}" /usr/bin/tegra-bootloader-update /opt/ota_package/bl_update_payload; then
    echo "ERR: bootloader update failed" >&2
    # Cleanup the mounted directory and remove the temporary directory
    cleanup
    exit 1
fi

# Update Sucessful
echo "Successful bootloader update"
# Cleanup the mounted directory and remove the temporary directory
cleanup
# Exit with sucess
exit 0

