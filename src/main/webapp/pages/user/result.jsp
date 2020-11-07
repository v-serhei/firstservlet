<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
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
        <div class="user-operation-page-content">
            <h3 class="text-center"><fmt:message key="${requestScope.operationType}"/></h3>
            <h4 class="text-center"><fmt:message key="${requestScope.operationResult}"/></h4>
            <div class="result-message-block">
                <h5> <fmt:message key="${requestScope.operationMessage}"/> </h5>
                <a href="${requestScope.btnLinkValue}" class="btn btn-sm btn-secondary "><fmt:message key="${requestScope.btnCaptionValue}"/> </a>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/footer.jsp"/>
</div>
</body>
</html>