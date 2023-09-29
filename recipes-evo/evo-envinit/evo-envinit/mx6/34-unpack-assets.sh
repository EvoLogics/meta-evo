#!/bin/sh

ASSETS_BASE="/opt/assets"
ASSETS_TGT="/opt"

ARX="$(ls ${ASSETS_BASE}/*.tgz 2> /dev/null || true)"
test -n "$ARX" || exit

cd ${ASSETS_TGT}

for i in ${ARX}; do
  echo "${i}" && tar zxvf ${i}
done
