#!/bin/sh

# If IMAGE_FEATURES has read-only-rootfs and no pregenerated keys found,
# sshd and dropbear will generate keys to /var instead of /etc
# we undo some changes done by read_only_rootfs_hook in rootfs-postcommands.bbclass

echo -n "Checking if sshd defaults exists... "
if [ -e /etc/default/ssh ]; then
  sed -i 's|/var/run/ssh|/etc/ssh|' /etc/default/ssh && echo "fixed!"
else
  echo "not found."
fi

echo -n "Checking if dropbear defaults exists... "
if [ -e /etc/default/dropbear ]; then
  sed -i 's|/var/lib/dropbear|/etc/dropbear|' /etc/default/dropbear && echo "fixed!"
else
  echo "not found."
fi