#!/bin/sh

#[ "$HOME" = "/" ] && { echo "Copying from skel should be run as user, exiting." && exit 0; }

RHOME="/home/root"

echo -n "Checking if .profile exists... "
if [ -e $RHOME/.profile ]; then
  echo "found!"
else
  echo -n "not found, copying from skel..."
  cp /etc/skel/.profile $RHOME && { chmod 600 $RHOME/.profile && echo "OK."; } || echo "not succeeded!"
fi

echo -n "Checking if .bashrc exists... "
if [ -e $RHOME/.bashrc ]; then
  echo "found!"
else
  echo -n "not found, copying from skel..."
  cp /etc/skel/.bashrc $RHOME && { chmod 600 $RHOME/.bashrc && echo "OK."; } || echo "not succeeded!"
fi
