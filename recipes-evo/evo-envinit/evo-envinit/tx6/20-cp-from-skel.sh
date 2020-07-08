#!/bin/sh

[ "$HOME" = "/" ] && { echo "Copying from skel should be run as user, exiting." && exit 0; }

echo -n "Checking if .profile exists... "
if [ -e $HOME/.profile ]; then
  echo "found!"
else
  echo -n "not found, copying from skel..."
  cp /etc/skel/.profile $HOME && { chmod 600 $HOME/.profile && echo "OK."; } || echo "not succeeded!"
fi

echo -n "Checking if .bashrc exists... "
if [ -e $HOME/.bashrc ]; then
  echo "found!"
else
  echo -n "not found, copying from skel..."
  cp /etc/skel/.bashrc $HOME && { chmod 600 $HOME/.bashrc && echo "OK."; } || echo "not succeeded!"
fi
