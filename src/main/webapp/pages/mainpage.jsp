<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ab" uri="customTagLib" %>
<!DOCTYPE html>
<html>
<c:import url="../WEB-INF/importjsp/header.jsp"/>
<body>
<c:import url="../WEB-INF/importjsp/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div id="content-div">
            <%--add if block for present songs albums or compilations--%>
            <c:import url="../WEB-INF/importjsp/songcontent.jsp"/>
            <ab:pagination linkValue="#" currentPage="1" totalPageCount="2"/>
        </div>

        <c:import url="../WEB-INF/importjsp/leftmenusong.jsp"/>
    </div>

</div>
<c:import url="../WEB-INF/importjsp/footer.jsp"/>
</body>
</html>