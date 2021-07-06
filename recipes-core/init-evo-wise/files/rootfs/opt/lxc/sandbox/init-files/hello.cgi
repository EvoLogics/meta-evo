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
        <h1> CGI shell script "Hello World!" </h1>
        <hr/>
        Source code at /usr/lib/cgi-bin/hello
        <pre>
END

sed -e 's/&/\&amp/g' \
    -e 's/>/\&gt;/g' \
    -e 's/</\&lt;/g' \
        $SCRIPT_FILENAME

cat << END
    </pre>
    <hr/>
    <h1> Enviroment variables </h1>
    <pre>
END

set

cat << END
    </pre>
    <p><a href='/'> Back</a></p>
    </body>
</html>
END
