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
<div id="reg-main-container">
    <div id="reg-controls-group">
        <form class="form-group" action="${pageContext.request.contextPath}/do/process_registration" method="post">
            <div id="reg-input-items">
                <h4 id="reg-label"><fmt:message key="reg.mainlabel"/></h4>
                <c:if test="${requestScope.regWrongName}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.wrong.name"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regExistName}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.exist.user"/></span>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="fieldUsername"><fmt:message key="reg.label.name"/></label>
                    <input type="text" class="form-control" id="fieldUsername"
                           name="fieldName"
                           pattern="[a-zA-Z\d]{1,40}"
                           minlength="1"
                           maxlength="40"
                           required
                           placeholder="<fmt:message key="reg.placeholder.name"/>"/>
                </div>

                <c:if test="${requestScope.regWrongPassword}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.wrong.password"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regDifferentPasswords}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.different.passwords"/></span>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="fieldPassword"><fmt:message key="reg.label.password"/></label>
                    <input type="password" class="form-control" id="fieldPassword"
                           name="fieldPassword"
                           pattern="^[a-zA-Z\d_#%,-:;=@`&\s\$\|\+\*\.\?]{6,40}$"
                           minlength="6"
                           maxlength="40"
                           required
                           placeholder="<fmt:message key="reg.placeholder.password"/>"/>
                </div>
                <div class="form-group">
                    <label for="fieldPassword2"><fmt:message key="reg.label.password2"/></label>
                    <input type="password" class="form-control" id="fieldPassword2"
                           name="fieldPassword2"
                           pattern="^[a-zA-Z\d_#%,-:;=@`&\s\$\|\+\*\.\?]{6,40}$"
                           minlength="6"
                           maxlength="40"
                           required/>
                </div>

                <c:if test="${requestScope.regWrongEmail}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.email.regex"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regExistEmail}">
                    <div id="reg-error-div">
                        <span class="reg-error-span bg-warning"><fmt:message key="reg.error.message.exist.email"/></span>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="fieldEmail"><fmt:message key="reg.label.email"/></label>
                    <input type="email" class="form-control" id="fieldEmail"
                           name="fieldEmail"
                           pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-z]{2,}$"
                           maxlength="60"
                           required
                           placeholder="<fmt:message key="reg.placeholder.email"/>"/>
                </div>
            </div>

            <div id="reg-submit">
                <button type="submit" class="btn btn-light" name="register">
                    <fmt:message key="reg.confirm.button"/>
                </button>
            </div>
        </form>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</body>
</html>