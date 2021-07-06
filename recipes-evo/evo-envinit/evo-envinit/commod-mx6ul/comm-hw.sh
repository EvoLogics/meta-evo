#!/bin/sh

SERIAL_PORT="/dev/ttymxc6"
BAUD=115200

REVISION="/etc/hwrevision"
BASE="/sys/class/gpio/export"
SHDN="/sys/class/gpio/gpio511/"
TE485="/sys/class/gpio/gpio510/"
MODE="/sys/class/gpio/gpio509/"
TEST="/sys/class/gpio/gpio508/"
PPS="/sys/class/gpio/gpio117/"


REVISION="/etc/hwrevision"

if test -f "$REVISION"; then
        HWREVISION=$(cat "$REVISION" | awk -F" " '{print $2}')
fi

compatibility()
{
        b=2.0
        awk 'BEGIN{if ('$HWREVISION'<'$b') exit 1}'
        if [ $? -eq 1 ]; then
                echo "ERROR: Feature Supported only on 2.0 or higher"
                exit 1
        fi
}


check_board()
{
	if [ ! -d "$TEST" ]; then
		echo "508" > "$BASE"
	fi

	echo "out" > "${TEST}direction"
	echo "1" > "${TEST}value"

	if ! grep -Fxq "1" "${TEST}value"; then
		echo "ERROR:RS232/485 Board not present!!"
		exit 1
	fi

	echo "0" > "${TEST}value"
}


usage()
{
	echo "Usage: comm-hw <serial, can_term, rs485_term, pps_sel> <232/485, on/off, on/off, gps/atmclk>"
}


pps_sel()
{
	if [ ! -d "$PPS" ]; then
		echo "117" > "$BASE"
	fi
		
	echo "out" > "${PPS}direction"

	if [ "$1" == "gps" ]; then
		echo "Setting GPS PPS to output"
		echo "0" > "${PPS}value"

	elif [ "$1" == "atmclk" ]; then
		echo "Setting ATM Clock PPS to output"
		echo "1" > "${PPS}value"
	else
		echo "ERROR:Unknown command!"
	fi
}

serial()
{
	if [ ! -d "$SHDN" ]; then
		echo "511" > "$BASE"
	fi
		
	echo "out" > "${SHDN}direction"
	echo "1" > "${SHDN}value"
	
	if [ "$1" == "232" ]; then
		echo "Setting to RS232 mode"

		if [ ! -d "$MODE" ]; then
			echo "509" > "$BASE"
		fi
		echo "out" > "${MODE}direction"
		echo "0" > "${MODE}value"

	elif [ "$1" == "485" ]; then
		echo "Setting to 485 Mode"

		if [ ! -d "$MODE" ]; then
			echo "509" > "$BASE"
		fi

		echo "out" > "${MODE}direction"
		echo "1" > "${MODE}value"

	else
		echo "ERROR:Unknown command!"
	fi
}


can_term()
{

	/bin/stty -F "$SERIAL_PORT" "$BAUD"

	if [ "$1" == "on" ]; then
		printf "TERM_EN=1\r\n" > "$SERIAL_PORT"
	elif [ "$1" == "off" ]; then
		printf "TERM_EN=0\r\n" > "$SERIAL_PORT"
	else
		echo "ERROR:Unknown command"
	fi
}

rs485_term()
{
	if  grep -Fxq "0" "${MODE}value"; then 
		echo "ERROR: In RS232 mode"
		exit 1
	fi

	if [ "$1" == "on" ]; then
		echo "setting rs485 term on"

		if [ ! -d "$TE485" ]; then       
			echo "510" > "$BASE"   
		fi

		echo "out" > "${TE485}direction"
		echo "0" > "${TE485}value"

	elif [ "$1" == "off" ]; then
		echo "setting rs485 term off"

		if [ ! -d "$TE485" ]; then
			echo "510" > "$BASE"
		fi

		echo "out" > "${TE485}direction"
		echo "1" > "${TE485}value"

	else
		echo "ERROR:Unknown command"
	fi  
}



if [ $# -ne 2 ]; then
	usage
	exit 1
fi

case $1 in
	serial)
		check_board
		serial $2
		;;
	can_term)
		compatibility  
		can_term $2
		;;
	rs485_term)
		check_board
		rs485_term $2
		;;
	pps_sel)
		compatibility
		pps_sel $2
		;;
	*)
		usage
		;;
esac
