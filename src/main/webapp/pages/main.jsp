<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>First servlet project</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
</head>
<body>
<div id="container">
    <div id="sidebar">
        <h4> ${sessionScope.userGreetings} </h4>
        <div id="loginblock" style="display:${sessionScope.displayLoginDiv}">
            <form action="login" method="get">
                <input type="submit" style="margin: 0 auto" id="login" name="action" value="Login">
            </form>
            <span style="alignment: center"> Or </span>
            <form action="registration" method="get">
                <input type="submit" id="register" name="action" value="Registration">
            </form>
            <br/>
        </div>
        <div id="logoutblock" style="display:${sessionScope.displayLogoutDiv}">
            <form action="logout" method="get">
                <input type="submit" id="logout" name="action" value="Logout">
            </form>
        </div>
    </div>
    <div id="header"> Пример работы с Java HTTP Servlet и JSP</div>
    <div id="content">
        <h3 style="align-content: center">Hello, Servlet World!</h3>
    </div>
</div>
<div id="footer">подвал

${sessionScope.userGreetings}
${sessionScope.displayLogoutDiv}
${sessionScope.displayLoginDiv}
${sessionScope.loginResult}

</div>
</body>
</html>