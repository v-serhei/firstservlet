<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div class="error-page-content">
            <div class="error-stack-trace">
                <br/>
                <h4 class="text-center"> <fmt:message key="error.page.header.message"/> </h4>
                <br/>
                <br/>
                <h4 class="text-center"> <fmt:message key="error.page.url.header"/> </h4>
                <h5 class="text-center"> ${sessionScope.lastUri}</h5>
                <br/>
                <h4 class="text-center"> <fmt:message key="error.page.message.header"/> </h4>
                <h5 class="text-center"><fmt:message key="access.violation.message"/></h5>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/footer.jsp"/>
</div>
</body>
</html>
