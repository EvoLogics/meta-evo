FILESEXTRAPATHS:prepend := "${THISDIR}/gpsd-tiny:"

SRC_URI:append = " \
  file://0001-allow-work-with-socat-pty.patch \
  "

SRC_URI:append:mx6ul-comm-module = " \
  file://gpsd.default \
  "

PACKAGECONFIG[tiny] = " \
  qt='no' \
  aivdm='no' \
  ashtech='no' \
  bluez='no' \
  ashtech='no' \
  cw='no' \
  earthmate='no' \
  evermore='no' \
  fury='no' \
  fv18='no' \
  garmin='no' \
  garmintxt='no' \
  geostar='no' \
  ipv6='no' \
  itrax='no' \
  libgpsmm='no' \
  mtk3301='no' \
  navcom='no' \
  oceanserver='no' \
  oncore='no' \
  passthrough='no' \
  pps='yes' \
  sirf='yes' \
  superstar2='no' \
  tnt='no' \
  tripmate='no' \
  ublox='yes' \
  python='no' \
"

do_install:append:mx6ul-comm-module() {
  install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/gpsd.default ${D}${sysconfdir}/default/gpsd.default

  # also install systemd service
}

