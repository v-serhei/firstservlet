<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-compilation-management">
    <div class="admin-page-inputs" id="admin-page-account-settings">
        <span class="admin-content-header"><fmt:message key="admin.compilation.manage.header"/></span>
        <div class="line-separator">
            <hr/>
        </div>
        <div class="compilation-content-management">
            <div class="compilation-delete-block">
                <form class="form-group" action="${pageContext.request.contextPath}/do/admin/compilation/delete_compilation" method="delete">
                    <h5 style="text-align: center"><fmt:message key="admin.compilation.manage.remove.caption"/></h5>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="compilation-select-block align-content-center form-group">
                        <label for="compilation-list"> <fmt:message key="admin.compilation.manage.select.caption"/></label>
                        <select class="custom-select mr-sm-2 admin-item-selector"
                                id="compilation-list" name="compilationId" size="1" required>
                            <option selected value=""></option>
                            <c:forEach var="compilation" items="${requestScope.compilationContentList}">
                                <option value="${compilation.id}">${compilation.compilationTitle}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="compilation-controls">
                        <input class="btn btn-sm btn-secondary bg-danger" type="submit"
                              name="action" value="<fmt:message key="admin.compilation.manage.delete.btn"/>"/>
                    </div>
                </form>
            </div>
            <c:if test="${requestScope.enableMessage}">
                <div class="admin-operation-message-block">
                    <span class="admin-message"><fmt:message key="${requestScope.resultMessage}"/></span>
                </div>
            </c:if>
        </div>
    </div>
</div>