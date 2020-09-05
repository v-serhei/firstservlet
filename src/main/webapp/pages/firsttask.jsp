<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>First servlet page</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="time" value="${System.currentTimeMillis()}">
    <input type="submit" name="submit" value="click">
</form>
</body>
</html>
