<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
<div id="container">
    <div>
        <form action="login" method="post">
            <div id="loginheader">
                <h3>Вход</h3>
            </div>
            <div id="regformdiv">
                <label for="usernamefield" style="align-self: center">Имя пользователья</label>
                <br/>
                <input id="usernamefield" type="text" name="fieldName">
                <br/>
                <label for="pass1field" style="align-self: center">Пароль</label>
                <br/>
                <input id="pass1field" type="password" name="fieldPassword">
                <br/>
                <input type="submit" name="action" value="Login">
                <br/>
                <br/>
                <span id="errorlabel"> ${loginErrorMessage} </span>
            </div>
        </form>
    </div>
</div>
</body>
</html>