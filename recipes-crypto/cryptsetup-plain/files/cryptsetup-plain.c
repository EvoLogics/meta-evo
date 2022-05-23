#include <err.h>
#include <stdio.h>
#include <unistd.h>     /* geteuid() */
#include <sys/types.h>
#include <libcryptsetup.h>


int main(int argc, char **argv)
{
	struct crypt_device *cd = NULL;
    int rc;

	if (geteuid())
		errx(1, "Need root");

	if (argc != 4)
		errx(2, "Usage: %s pass-file block-dev dm-name", argv[0]);

    if ((rc = crypt_init(&cd, argv[2])))
        errx(-rc, "crypt_init():");

	rc = crypt_format(cd, CRYPT_PLAIN,
			 "aes", "cbc-essiv:sha256",
			 NULL, NULL,
			 256 / 8,
			 NULL);

    if (rc < 0)
        errx(-rc, "crypt_format():");

    rc = crypt_activate_by_keyfile_device_offset(cd, argv[3],
            CRYPT_ANY_SLOT, argv[1], 0, 0, 0);
    if (rc < 0)
        errx(-rc, "crypt_activate_by_keyfile():");

	crypt_free(cd);
    return 0;
}
