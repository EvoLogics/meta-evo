#!/bin/sh

DUNE_BASE="/opt/dune"
SYSNAME=$(cat /etc/hostname)

echo -n "Checking if dune configuration folder exists... "
if [ -d ${DUNE_BASE}/etc/ ]; then
  echo "found!"
else
  echo "not found, exiting!"
  exit 0
fi

echo -n "Checking if hostname matches with dune configuration... "
if [ -e ${DUNE_BASE}/etc/${SYSNAME}.ini ]; then
  echo "found!"
else
  echo "not found!"
  echo "%% Manual intervention may be required %%"
fi

echo -n "Checking for local.ini... "
if [ -e ${DUNE_BASE}/etc/local.ini ]; then
  echo "found!"
else
  echo -n "not found, creating... "
  cat >> ${DUNE_BASE}/etc/local.ini << EOF && echo "OK."
# Place your local configuration here, for example temporary parameter
# overrides that do not belong in the repository.
EOF
fi

echo -n "Checking for config.ini... "
if [ -e ${DUNE_BASE}/etc/config.ini ]; then
  echo "found!"
else
  echo -n "not found, creating... "
  cat >> ${DUNE_BASE}/etc/config.ini << EOF && echo "OK."
# Automatically generated file, do not edit it. If you need some local
# configuration tuning, use local.ini for this purpose.

[Require ${SYSNAME}.ini]
[Include local.ini]
EOF
fi
