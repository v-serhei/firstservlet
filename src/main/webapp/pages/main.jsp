<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>First servlet project</title>
    <style type="text/css">
        body {
            font: 10pt Arial, Helvetica, sans-serif; /* Шрифт на веб-странице */
            background: #e1dfb9; /* Цвет фона */
        }

        h2 {
            font-size: 1.1em; /* Размер шрифта */
            color: #800040; /* Цвет текста */
            margin-top: 0; /* Отступ сверху */
        }

        h3 {
            text-align: center;
        }

        #container {
            width: 80%; /* Ширина слоя */
            height: 900px;
            margin: 0 auto; /* Выравнивание по центру */
            background: #f0f0f0; /* Цвет фона левой колонки */

        }

        #header {
            font-size: 2.2em; /* Размер текста */
            text-align: center; /* Выравнивание по центру */
            padding: 5px; /* Отступы вокруг текста */
            background: #8fa09b; /* Цвет фона шапки */
            color: #ffe; /* Цвет текста */
        }

        #sidebar {
            margin-top: 10px;
            width: 150px; /* Ширина слоя */
            padding: 0 10px; /* Отступы вокруг текста */
            float: left; /* Обтекание по правому краю */
        }

        #content {
            margin-left: 180px; /* Отступ слева */
            padding: 10px; /* Поля вокруг текста */
            background: #fff; /* Цвет фона правой колонки */
        }

        #footer {
            width: 80%;
            margin: 0 auto;
            background: #8fa09b; /* Цвет фона подвала */
            color: #fff; /* Цвет текста */
            clear: left; /* Отменяем действие float */
        }

        #auth {
            display: ${authBlockVisible};
        }

        #logout {
            display: ${logoutBlockVisible};
        }
        #errLogin {
            display: ${errorVisible};
        }
    </style>
</head>
<body>
<div id="container">
    <div id="header"> Пример работы с Java HTTP Servlet и JSP</div>
    <div id="sidebar">
        <div id="auth">
            <form action="${pageContext.request.contextPath}/auth-action" method="post">
                <h3>Авторизация</h3>
                <div id="errLogin">
                    <p> Неверное имя пользователя или пароль</p>
                </div>
                <hr/>
                <label for="userName">Имя пользователя</label>
                <input id="userName" type="text" name="username">
                <hr/>
                <label for="pswd">Пароль</label>
                <input id="pswd" type="password" name="password">
                <hr/>
                <input type="submit" name="action" value="logIn">
            </form>
        </div>
        <div id="logout">
            <form action="${pageContext.request.contextPath}/auth-action" method="post">
                <h3>Вы вошли на сайт как ${username}</h3>
                <hr/>
                <input type="submit" name="action" value="logout">
            </form>
        </div>
    </div>
    <div id="content">
        <h2> Добро пожаловать, ${username} </h2>
    </div>
</div>
<div id="footer">подвал</div>
</body>
</html>