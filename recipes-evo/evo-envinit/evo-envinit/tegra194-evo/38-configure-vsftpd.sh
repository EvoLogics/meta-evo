#!/bin/sh

VSFTPD_BASE="/usr/sbin/vsftpd"
VSFTPD_USER_LIST="/etc/vsftpd/user_list"
VSFTPD_CONF_FILE="/etc/vsftpd.conf"

echo "Checking if vsftpd is installed.."

if [ -e ${VSFTPD_BASE} ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo "Creating vsftpd userlist..."

touch ${VSFTPD_USER_LIST}
echo "# vsftpd userlist" >> ${VSFTPD_USER_LIST}

# Add each user to userlist
for entry in "/etc/vsftpd/user_config_dir/"*
do
  echo "$(basename "${entry}")" >> ${VSFTPD_USER_LIST}
done
