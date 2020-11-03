<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<c:import url="../WEB-INF/pages/importjsp/header.jsp"/>
<body>
<c:import url="../WEB-INF/pages/importjsp/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <%--add if block for present songs albums or compilations--%>
        <c:import url="../WEB-INF/pages/importjsp/compilationcontent.jsp"/>
        <c:import url="../WEB-INF/pages/importjsp/rightmenucompilation.jsp"/>
    </div>

</div>
<c:import url="../WEB-INF/pages/importjsp/footer.jsp"/>
</body>
</html>