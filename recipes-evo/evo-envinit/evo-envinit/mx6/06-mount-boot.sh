#!/bin/sh

ROOT_PARTUUID=$(findmnt -fn -o PARTUUID /)
MMC_ID=$(echo ${ROOT_PARTUUID} | cut -f1 -d'-')
PART_NR=$(echo ${ROOT_PARTUUID} | cut -f2 -d'-')

if [ ${PART_NR} == "02" -o ${PART_NR} == "03" ]; then
  # either single root or 'a' partition
  BOOT_NR="01"
elif [ ${PART_NR} == "04" ]; then
  # 'b' partition
  BOOT_NR="02"
else
  # unknown
  exit 0
fi

echo "Rootfs PARTUUID=${MMC_ID}-${PART_NR}."
echo -n "Adding ${MMC_ID}-${BOOT_NR} as boot partition... "

echo "PARTUUID=${MMC_ID}-${BOOT_NR} /boot/ auto defaults,ro 0 0" \
  >> /etc/fstab \
  && echo "ok." || echo "failed!"
mount -a
