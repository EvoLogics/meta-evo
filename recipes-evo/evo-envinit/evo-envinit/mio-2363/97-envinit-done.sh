#!/bin/sh

ENVINIT_DONE=/etc/envinit-done

echo "Marking initialization done."
touch ${ENVINIT_DONE}
