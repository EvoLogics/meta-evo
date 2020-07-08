#! /bin/sh 


CMD=$1
monit=/usr/bin/monit

if [  "$CMD" == "start"  -o   "$CMD" == "reload"  -o "$CMD" == "stop" ]; then
  grep -i ' / ' /etc/fstab | grep -qe '[, ] *ro[, ] *'
  if [ $? -eq 0 ]; then
    monit_conf="/etc/monitrc-ro-rootfs"
  else
    monit_conf="/etc/monitrc"
  fi
  
  monit_args="-c ${monit_conf}"

  test -x "$monit" || exit 0

  if [ "$CMD" == "start" ]; then
    $monit $monit_args &
    RETVAL=$?

  elif ["$CMD" == "reload"]; then
    $monit $monit_args reload
    RETVAL=$?

  elif ["$CMD" == "stop"]; then
    $monit $monit_args quit
    RETVAL=$?

  fi
  exit $RETVAL

fi
exit 1

