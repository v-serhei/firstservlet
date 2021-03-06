<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/common/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/common/topmenu.jsp"/>
<div id="login-main-container">
    <div id="login-controls-group">
        <form class="form-group" action="${pageContext.request.contextPath}/do/process_login" method="post">
            <div id="login-input-items">
                <h4 id="login-label"><fmt:message key="login.mainlabel"/></h4>
                <div class="form-group">
                    <label for="fieldUsername"><fmt:message key="login.label.name"/></label>
                    <input type="text"
                           class="form-control"
                           id="fieldUsername"
                           name="fieldName"
                           pattern="[a-zA-Z\d]{1,40}"
                           minlength="1"
                           maxlength="40"
                           required
                           placeholder="<fmt:message key="login.placeholder.name"/>"/>
                </div>
                <div class="form-group">
                    <label for="fieldUserPassword"><fmt:message key="login.label.password"/></label>
                    <input type="password"
                           class="form-control"
                           id="fieldUserPassword"
                           name="fieldPassword"
                           pattern="^[a-zA-Z\d_#%,-:;=@`&\s\$\|\+\*\.\?]{6,40}$"
                           minlength="6"
                           maxlength="40"
                           required
                           placeholder="<fmt:message key="login.placeholder.password"/>"/>
                </div>
            </div>
            <c:if test="${requestScope.loginFail}">
                <div id="login-error-div">
                    <span class="login-error-span bg-warning"><fmt:message key="login.error.message"/></span>
                </div>
            </c:if>
            <div id="login-submit">
                <input type="submit" class="btn btn-light" name="confirm" value="<fmt:message key="login.confirm.button"/>"/>
            </div>
        </form>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</body>
</html>