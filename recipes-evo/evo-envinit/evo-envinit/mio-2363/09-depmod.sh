#!/bin/sh

# workaround to use all modules copied separately
echo -n "Generating modules.dep and map files... "
depmod -a && echo "ok."
