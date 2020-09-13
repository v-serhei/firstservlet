<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
</head>
<body>
<div id="container">
    <div>
        <form action="registration" method="post">
            <div id="registerheader">
                <h3>Регистрация нового пользователя</h3>
            </div>
            <div id="regdivelements">
                <label for="usernamefield">Имя пользователья</label>
                <br/>
                <input id="usernamefield" type="text" name="fieldName">
                <br/>
                <label for="pass1field">Пароль</label>
                <br/>
                <input id="pass1field" type="password" name="fieldPassword">
                <br/>
                <label for="pass2field">Повторите пароль</label>
                <br/>
                <input id="pass2field" type="password" name="fieldPassword2">
                <br/>
                <label for="emailfield">Почта</label>
                <br/>
                <input id="emailfield" type="text" name="fieldEmail">
                <br/>
                <br/>
                <br/>
                <input id="registerbtn" type="submit" name="action" value="Register">
                <br/>
                <br/>
                <span id="errorlabel" style="margin-top: 10px"> ${registrationErrorMessage} </span>
            </div>
        </form>
    </div>
</div>
</body>
</html>