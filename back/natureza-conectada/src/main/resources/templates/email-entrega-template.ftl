<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Java Mail</title>
</head>

<body style="background-color: #ffffff; font-family: Arial, Helvetica, sans-serif; color: #000000;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
        <tr>
            <td align="center" valign="top" bgcolor="#ffffff" style="background-color: #ffffff;">
                <br> <br>
                <!-- Add Image Here -->
                <img src="https://firebasestorage.googleapis.com/v0/b/e-commerce-91ed5.appspot.com/o/fotos%2FNaturezaConecta.JPG?alt=media&token=4a901bca-a033-49c3-9fe7-802b70a98e66" alt="">
                <br> <br>
                <br> <br>
                <table width="600" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="center" valign="top" bgcolor="#d3be6c"
                            style="background-color: #ffffff; font-size: 13px; padding: 0px 15px 10px 15px;">

                            <div style="font-size: 18px; color: #0000ff; font-weight: bold;">
                                <b>Olá ${nome},</b>
                            </div>

                            <div style="font-size: 16px; color: #000000;">
                                ${mensagem}
                                .
                            </div>

                            <div style="font-size: 16px; color: #000000;">
                                Qualquer dúvida é só contatar o suporte pelo e-mail ${email}

                            </div>

                            <div style="font-size: 16px; color: #000000; margin-top: 20px;">
                                Att,<br>
                                Sistema.
                            </div>
                        </td>
                    </tr>
                </table>
                <br> <br>
            </td>
        </tr>
    </table>
</body>
</html>


