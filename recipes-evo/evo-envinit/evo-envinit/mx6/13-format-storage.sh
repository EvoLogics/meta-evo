#!/bin/sh


check_blk()
{
  bname=$1
  bpath=$2
  echo -n "Checking if ${bname} ${bpath} exists... "
  if [ -b ${bpath} ]; then
    echo "found"
    return 0
  else
    echo "not found!"
    return 1
  fi
}

check_part()
{
  ppath=$1
  echo -n "  Checking if it is partitioned... "
  if [ -b ${ppath} ]; then
    fstype=$(lsblk -n -o FSTYPE ${ppath})
    if [ -z ${fstype} ]; then
      echo "yes, not formatted"
      format_part ${ppath}
      return 0
    else      
      echo "yes, formatted as ${fstype}"
      if [ "${fstype}" != "ext4" ]; then
        format_part ${ppath}
        return 0
      fi
    fi
  else
    echo "no!"
    return 1
  fi

}

partition()
{
  bpath=$1
  echo ";" | sfdisk ${bpath}
}

format_part()
{
  ppath=$1
  agreed=$2
  if [ "${agreed}" == "y" ]; then
    mkfs.ext4 -F -m0 ${ppath} && return 0
  fi

  echo -e "\e[31mWARNING: formatting will destroy ALL data contained in ${ppath}!!!\e[0m"
  mkfs.ext4 -m0 ${ppath}
}

check_all()
{
  bname=$1
  bpath=$2
  ppath=$3

  check_blk ${bname} ${bpath} || return
  check_part ${ppath} && return
  partition ${bpath} && format_part ${ppath} y
}


check_all ssd /dev/sda /dev/sda1
check_all sdcard /dev/mmcblk1 /dev/mmcblk1p1
