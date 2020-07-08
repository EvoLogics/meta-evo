#!/bin/sh

ROOTTMP=$(lsblk -l -o NAME,MOUNTPOINT | grep '/home/root$')
ROOTPART=/dev/${ROOTTMP%% */home/root}
#Get Partition Number
PART_NUMBER=$(udevadm info --query=property --name=${ROOTPART} | grep '^ID_PART_ENTRY_NUMBER=' | cut -d'=' -f2)

ROOTDEV=${ROOTPART%p${PART_NUMBER}}

# Use sfdisk to extend partition to all available free space on device.
flock $ROOTDEV sfdisk -f $ROOTDEV -N ${PART_NUMBER} <<EOF
,+1490M
EOF

sleep 5

# Wait for all pending udev events to be handled
udevadm settle

sleep 5

# detect the changes to partition (we extended it).
flock $ROOTDEV partprobe $ROOTDEV

# remount the root partition in read write mode
mount -o remount,rw $ROOTPART

# Finally grow the file system on root partition
resize2fs $ROOTPART

exit 0fs
