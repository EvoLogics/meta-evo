/* lxc console login
 * Copyright (c) 2012 by komar <komar@evologics.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include <stdio.h>
#include <unistd.h>    /* execlp() */
#include <stdlib.h>    /* getenv() */
#include <err.h>       /* err() */
#include <sys/types.h> /* getuid() */

#define SHELL   "/usr/bin/lxc-console"
#define SB_NAME "sandbox"
#define HOME    "/home/root"

int main()
{
    char *jail = getenv("HOME");

    if (jail == NULL)
        errx(1, "variable HOME undefined");

    /* if (chdir(HOME) < 0) */
        /* err(3, "chdir()"); */

    if (setuid(geteuid()) < 0)
        err(4, "seteuid()");

    /* don't work? setup /bin/ish in images-files/lxc/pre-inst.d/20-lxc-inst-guest.sh */
    setenv("HOME",  HOME, 1);
    setenv("SHELL", "/bin/bash", 1);

    execl(SHELL, SHELL, "-e", "0","-qn", SB_NAME, NULL);
    err(6, "execlp()");

    return 0;
}

