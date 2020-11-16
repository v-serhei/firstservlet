<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/common/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/common/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div class="user-operation-page-content">
            <h3 class="text-center"><fmt:message key="${sessionScope.operationType}"/></h3>
            <h4 class="text-center"><fmt:message key="${sessionScope.operationResult}"/></h4>
            <div class="result-message-block">
                <h5> <fmt:message key="${sessionScope.operationMessage}"/> </h5>
                <a href="${sessionScope.btnLinkValue}" class="btn btn-sm btn-secondary "><fmt:message key="${sessionScope.btnCaptionValue}"/> </a>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</div>
</body>
</html>