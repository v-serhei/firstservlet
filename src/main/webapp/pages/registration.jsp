<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="jsptemplate/header.jsp"/>
<body>
<div id="reg-main-container">
    <div id="reg-controls-group">
        <div id="reg-input-items">
            <h4 id="reg-label"><fmt:message key="reg.mainlabel"/> </h4>
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
                       name="fieldPassword"/>
            </div>

            <div class="form-group">
                <label for="fieldEmail"><fmt:message key="reg.label.email"/></label>
                <input type="text" class="form-control" id="fieldEmail"
                       name="fieldName" placeholder="<fmt:message key="reg.placeholder.email"/>"/>
            </div>
        </div>
        <div id="reg-error-div">
            <span id="reg-error-span">какой-то вариант ошибки</span>
        </div>
        <div id="reg-submit">
            <form action="registration" method="post">
                <input type="submit" class="btn btn-light" name="action" value="<fmt:message key="reg.confirm.button"/>"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    <c:import url="/js/jquery-3.5.1.min.js"/>
    <c:import url="/css/bootstrap/js/bootstrap.min.js"/>
</script>
</body>
</html>