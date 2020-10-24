<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="../WEB-INF/importjsp/header.jsp"/>
<body>
<c:import url="../WEB-INF/importjsp/topmenu.jsp"/>
<div id="login-main-container">
    <div id="login-controls-group">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div id="login-input-items">
                <h4 id="login-label"><fmt:message key="login.mainlabel"/></h4>
                <div class="form-group">
                    <label for="fieldUsername"><fmt:message key="login.label.name"/></label>
                    <input type="text" class="form-control" id="fieldUsername"
                           name="fieldName" placeholder="<fmt:message key="login.placeholder.name"/>">
                </div>
                <div class="form-group">
                    <label for="fieldUserPassword"><fmt:message key="login.label.password"/></label>
                    <input type="password" class="form-control" id="fieldUserPassword"
                           name="fieldPassword" placeholder="<fmt:message key="login.placeholder.password"/>">
                </div>
            </div>
            <c:if test="${requestScope.loginFail}">
                <div id="login-error-div">
                    <span class="login-error-span"><fmt:message key="login.error.message"/></span>
                </div>
            </c:if>
            <div id="login-submit">
                <input type="hidden" class="btn btn-light" name="action" value="login"/>
                <input type="submit" class="btn btn-light" name="confirm" value="<fmt:message key="login.confirm.button"/>">
            </div>
        </form>
    </div>
</div>
<c:import url="../WEB-INF/importjsp/footer.jsp"/>
</body>
</html>