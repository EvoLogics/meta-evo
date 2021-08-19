DBFILE=/etc/pureftpd.pdb

echo -n "Check if pure-ftpd db is needed... "
# create database if missing
[ ! -e ${DBFILE} ] && { which pure-pw > /dev/null && pure-pw mkdb ${DBFILE} && echo "created!" || echo "no."; } || echo "exists."

