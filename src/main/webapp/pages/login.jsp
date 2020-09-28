<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="jsptemplate/header.jsp"/>
<body>
<div id="login-main-container">
    <div id="login-controls-group">
        <form action="login" method="post">
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
            <c:if test="${requestScope.loginError}">
                <div id="login-error-div">
                    <span id="login-error-span"><fmt:message key="login.error.message"/></span>
                </div>
            </c:if>
            <div id="login-submit">
                <button class="btn btn-light" name="action" value="LOGIN">
                    <fmt:message key="login.confirm.button"/>
                </button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    <c:import url="/js/jquery-3.5.1.min.js"/>
    <c:import url="/css/bootstrap/js/bootstrap.min.js"/>
</script>
</body>
</html>