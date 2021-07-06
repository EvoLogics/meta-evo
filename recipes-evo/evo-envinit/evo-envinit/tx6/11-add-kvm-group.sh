#!/bin/sh

echo -n "Checking if kvm group exists... "
grep -q kvm /etc/group
if [ $? -eq 0 ]; then
  echo "found!"
else
  echo -n "not found, adding... "
  addgroup --system kvm && echo "OK." || echo "fail!"
fi
