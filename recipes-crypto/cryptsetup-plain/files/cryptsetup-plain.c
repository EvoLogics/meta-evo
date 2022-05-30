#include <err.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>     /* geteuid() */
#include <sys/types.h>
#include <libcryptsetup.h>

void usage() {

}
int main(int argc, char **argv)
{
    struct crypt_device *cd = NULL;
    int rc;

    if (argc != 3 && argc != 5) {
        errx(2, "Usage: %s [open pass-file block-dev dm-name]|[close dm-name]", argv[0]);
    }

    if (geteuid())
        errx(1, "Need root");

    if (!strcmp(argv[1], "open")) {
        if ((rc = crypt_init(&cd, argv[3])))
            errx(-rc, "crypt_init():");

        rc = crypt_format(cd, CRYPT_PLAIN,
                "aes", "cbc-essiv:sha256",
                NULL, NULL,
                256 / 8,
                NULL);

        if (rc < 0)
            errx(-rc, "crypt_format():");

        rc = crypt_activate_by_keyfile_device_offset(cd, argv[4],
                CRYPT_ANY_SLOT, argv[2], 0, 0, 0);
        if (rc < 0)
            errx(-rc, "crypt_activate_by_keyfile():");

        crypt_free(cd);
    } else if (!strcmp(argv[1], "close")) {
        rc = crypt_init_by_name(&cd, argv[2]);

        if (rc == 0)
            rc = crypt_deactivate_by_name(cd, argv[2], 0);

        crypt_free(cd);
    } else {
        errx(1, "Unknown command");
    }

    return 0;
}
