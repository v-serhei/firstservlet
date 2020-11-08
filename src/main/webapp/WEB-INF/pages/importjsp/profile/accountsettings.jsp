<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<c:set var="user" value="${sessionScope.user}"/>
<div id="profile-settings-inputs">
    <div class="user-settings-header">

        <span class="user-details-table-caption"><fmt:message key="profile.settings.user.details.caption"/></span>
        <table class="user-details-table">
            <tr>
                <td>
                    <span class="user-item"><fmt:message key="profile.settings.role"/></span>
                </td>
                <td>
                    <span class="item-value">${sessionScope.user.roleDescription} </span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="user-item"><fmt:message key="profile.settings.reg.date.caption"/></span>
                </td>
                <td>
                    <span class="item-value">${sessionScope.user.registrationDate}</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="user-item"><fmt:message key="profile.settings.discount.caption"/></span>
                </td>
                <td>
                    <span class="item-value">${sessionScope.user.discount}</span>
                </td>
            </tr>
        </table>
    </div>
    <form action="${pageContext.request.contextPath}/do/profile/update_password" method="post">
        <div class="form-group">
            <label for="fieldUserPassword"><fmt:message key="profile.change.new.password"/></label>
            <input type="password" class="form-control" id="fieldUserPassword"
                   name="fieldPassword" placeholder="<fmt:message key="reg.placeholder.password"/>">
        </div>
        <div class="form-group">
            <label for="fieldUserPassword"><fmt:message key="reg.label.password2"/></label>
            <input type="password" class="form-control" id="fieldUserPassword2"
                   name="fieldPassword2">
        </div>
        <div class="profile-settings-submit">
            <input type="submit" class="btn btn-light" name="action" value="<fmt:message key="confirm.btn.caption"/>">
        </div>
    </form>
    <form action="${pageContext.request.contextPath}/do/profile/update_email" method="post">
        <div class="form-group">
            <label for="fieldUserMail"><fmt:message key="reg.label.email"/></label>
            <input type="text" class="form-control" id="fieldUserMail"
                   name="fieldEmail" value="${user.email}">
        </div>
        <div class="profile-settings-submit">
            <input type="submit" class="btn btn-light" name="action" value="<fmt:message key="confirm.btn.caption"/>">
        </div>
    </form>
    <c:if test="${sessionScope.showMessage}">
        <div class="profile-operation-message">
            <c:if test="${sessionScope.updateUser}">
                <span><fmt:message key="profile.settings.operation.successful"/></span>
            </c:if>
            <c:if test="${!sessionScope.updateUser}">
                <span><fmt:message key="profile.settings.operation.failed"/></span>
                <c:if test="${requestScope.regWrongPassword}">
                    <div id="reg-error-div">
                        <span class="reg-error-span"><fmt:message key="reg.error.message.wrong.password"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regDifferentPasswords}">
                    <div id="reg-error-div">
                        <span class="reg-error-span"><fmt:message key="reg.error.message.different.passwords"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regWrongEmail}">
                    <div id="reg-error-div">
                        <span class="reg-error-span"><fmt:message key="reg.error.message.email.regex"/></span>
                    </div>
                </c:if>
                <c:if test="${requestScope.regExistEmail}">
                    <div id="reg-error-div">
                        <span class="reg-error-span"><fmt:message key="reg.error.message.exist.email"/></span>
                    </div>
                </c:if>
            </c:if>
        </div>
    </c:if>
</div>