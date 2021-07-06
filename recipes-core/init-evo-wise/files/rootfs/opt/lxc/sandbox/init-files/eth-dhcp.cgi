#!/bin/sh

printf "Content-type: text/html\r\n\r\n"
cat << END
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title> Hello World </title>
            <style type="text/css">
                pre  { color: #ffffff; background-color: #000000; }
            </style>
        </head>
    <body>
        <h1> Example of control network interface eth0:dhcp </h1>
        <hr />
        <table width="100%" border="0" cellspacing="20" align="center">
        <div>
            <pre>
END

case "${QUERY_STRING}" in
    action=up)   sudo /usr/local/bin/eth-ctl up   ;;
    action=down) sudo /usr/local/bin/eth-ctl down ;;
esac

cat << END
            </pre>
        </div>
        <div class="btn-group">
            <table><tr>
            </tr><tr>
                Control eth0:dhcp
            </tr><tr>
                <td align ="left" colspan="2"><form action='' method='get'> <input class='button' type='submit' name='action' value='up'   /> </form></td>
                <td align ="left" colspan="2"><form action='' method='get'> <input class='button' type='submit' name='action' value='down' /> </form></td>
            </tr></table>
        </div>
        <hr/>
        <div>
            <pre> $(sudo /usr/local/bin/eth-ctl status) </pre>
            Status
            <form action='' method='get'> <input class='button' type='submit' name='action' formtarget='_self' value='refresh' /></form>
        </div>
        <hr/>
        <div>
            <p><a href='/'> Back</a></p>
        </div>
    </body>
</html>
END
