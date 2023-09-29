#!/bin/sh

echo "Scheduling reboot in 60 seconds..."
{ sleep 60; reboot; } &
