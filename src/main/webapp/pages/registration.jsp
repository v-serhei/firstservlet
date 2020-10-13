<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="../WEB-INF/importjsp/header.jsp"/>
<body>
<div id="reg-main-container">
    <div id="reg-controls-group">
        <form action="register" method="post">
            <div id="reg-input-items">
                <h4 id="reg-label"><fmt:message key="reg.mainlabel"/></h4>
                <div class="form-group">
                    <label for="fieldUsername"><fmt:message key="reg.label.name"/></label>
                    <input type="text" class="form-control" id="fieldUsername"
                           name="fieldName" placeholder="<fmt:message key="reg.placeholder.name"/>"/>
                </div>
                <div class="form-group">
                    <label for="fieldPassword"><fmt:message key="reg.label.password"/></label>
                    <input type="password" class="form-control" id="fieldPassword"
                           name="fieldPassword" placeholder="<fmt:message key="reg.placeholder.password"/>"/>
                </div>
                <div class="form-group">
                    <label for="fieldPassword2"><fmt:message key="reg.label.password2"/></label>
                    <input type="password" class="form-control" id="fieldPassword2"
                           name="fieldPassword2"/>
                </div>
                <div class="form-group">
                    <label for="fieldEmail"><fmt:message key="reg.label.email"/></label>
                    <input type="text" class="form-control" id="fieldEmail"
                           name="fieldEmail" placeholder="<fmt:message key="reg.placeholder.email"/>"/>
                </div>
            </div>
            <c:if test="${requestScope.regResult}">
                <div id="reg-error-div">
                    <span id="reg-error-span"><fmt:message key="${requestScope.regErrorMessage}"/></span>
                </div>
            </c:if>
            <div id="reg-submit">
                <button type="submit" class="btn btn-light" name="action" value="REGISTER">
                    <fmt:message key="reg.confirm.button"/>
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>