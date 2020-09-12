<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
<div id="container">
    <div>
        <form action="controller" method="post">
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
                <input id="pass1field" type="password" name="fieldName">
                <br/>
                <input type="submit" name="loginbtn" value="Login">
            </div>

        </form>
        <label id="errorlabel"> ${loginerrormessage} </label>
    </div>
</div>
</body>
</html>