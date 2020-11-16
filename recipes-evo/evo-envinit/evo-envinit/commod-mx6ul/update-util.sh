#!/bin/sh

which barebox-state > /dev/null || { echo "barebox-state not found, exiting." && exit 0; }

usage()
{
        echo "Usage: 
	update-util [option]
	-mg , --mark-good
	-sa , --switch-active
	-gn , --get-next
"
}

mark_good()
{
	SYSTEM0_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system0.priority)
	SYSTEM1_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system1.priority)
	
	if [ $SYSTEM0_PRIORITY -gt $SYSTEM1_PRIORITY ]
	then 
		echo "Setting system0 as good"
		REMAINING_ATTEMPTS=$(/usr/bin/barebox-state -g bootstate.system0.remaining_attempts)
		echo $REMAINING_ATTEMPTS

		if [ $REMAINING_ATTEMPTS -lt 2 ]
		then
			echo "Setting Remaining Attempts to 10"
			/usr/bin/barebox-state -s bootstate.system0.remaining_attempts=10
		fi
	else
		echo "Setting system1 as good"
		REMAINING_ATTEMPTS=$(/usr/bin/barebox-state -g bootstate.system1.remaining_attempts)
		echo $REMAINING_ATTEMPTS 

		if [ $REMAINING_ATTEMPTS -lt 2 ]
		then
			echo "Setting Remaining Attempts to 10"
                        /usr/bin/barebox-state -s bootstate.system1.remaining_attempts=10
		fi
	fi

	#Check the value of remaining attempts
	#If value is greater than 1 do nothing
	#else reset remaining attempts to 10
}

get_next()
{
	SYSTEM0_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system0.priority)                    
    SYSTEM1_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system1.priority)

	if [ $SYSTEM0_PRIORITY -gt $SYSTEM1_PRIORITY ]                                              
        then
		echo "system1"
	else
		echo "system0"
	fi
}

switch_active()
{
	SYSTEM0_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system0.priority)
	SYSTEM1_PRIORITY=$(/usr/bin/barebox-state -g bootstate.system1.priority)

	if [ $SYSTEM0_PRIORITY -gt $SYSTEM1_PRIORITY ]
	then
		echo "setting system1 as active"
		/usr/bin/barebox-state -s bootstate.system1.priority=21
		/usr/bin/barebox-state -s bootstate.system0.priority=20

		REMAINING_ATTEMPTS=$(/usr/bin/barebox-state -g bootstate.system1.remaining_attempts)

		if [ $REMAINING_ATTEMPTS -lt 2 ]
		then
			/usr/bin/barebox-state -s bootstate.system1.remaining_attempts=10
		fi
	else
		echo "setting system0 as active"
		/usr/bin/barebox-state -s bootstate.system1.priority=20
		/usr/bin/barebox-state -s bootstate.system0.priority=21
		REMAINING_ATTEMPTS=$(/usr/bin/barebox-state -g bootstate.system0.remaining_attempts)

		if [ $REMAINING_ATTEMPTS -lt 2 ]
		then
			/usr/bin/barebox-state -s bootstate.system0.remaining_attempts=10
		fi
	fi
}

if [ $# -eq 0 ]
then
	echo "No arguments supplied"
	usage
	exit 0
fi

case $1 in
	-mg | --mark-good)
		mark_good
		;;
	-sa | --switch-active)
		switch_active
		;;
	-gn | --get-next)
		get_next
		;;
	*)
		usage
		;;
esac
