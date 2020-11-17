<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-singer-management">
    <div class="admin-page-inputs">
        <span class="admin-content-header"><fmt:message key="admin.singer.manage.header"/></span>
        <div class="line-separator">
            <hr/>
        </div>
        <div class="singer-content-management">
            <div class="singer-update-block">
                <h5 style="text-align: center"><fmt:message key="admin.singer.manage.update.block.header"/></h5>
                <div class="line-separator">
                    <hr/>
                </div>
                <label for="singer-list" style="margin: 0 auto"> <fmt:message
                        key="admin.singer.manage.select.caption"/> </label>
                <form class="form-group" action="${pageContext.request.contextPath}/do/admin/singer_management/update_singer" method="put">
                    <div class="singer-select-block form-group">
                        <select class="custom-select mr-sm-2 admin-item-selector"
                                id="singer-list" name="singerName" size="1" required>
                            <option selected value=""></option>
                            <c:forEach var="singer" items="${requestScope.singerList}">
                                <option value="${singer.singerName}">${singer.singerName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="singer-update-inputs form-group">
                        <label for="singer-update-name"> <fmt:message
                                key="admin.singer.manage.entered.name.caption"/></label>
                        <input class="form-control" id="singer-update-name"
                               type="text"
                               name="singerUpdateName"
                               pattern="[a-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                               required
                               minlength="1"
                               maxlength="80"/>
                        <div class="admin-action-controls">
                            <input class="btn btn-sm btn-secondary bg-warning" type="submit" name="update"
                                   value="<fmt:message key="admin.manage.update.btn"/>"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <h5 style="text-align: center"><fmt:message key="admin.singer.manage.create.block.header"/></h5>
            <div class="line-separator">
                <hr/>
            </div>
            <div class="singer-create-block">
                <div class="singer-update-block">
                    <div class="singer-create-inputs">
                        <form class="form-group" action="${pageContext.request.contextPath}/do/admin/singer_management/create_singer" method="post">
                            <label for="singer-create-name" style="margin: 0 auto"><fmt:message
                                    key="admin.singer.manage.entered.name.caption"/></label>
                            <input class="form-control" id="singer-create-name"
                                   type="text"
                                   name="singerCreateName"
                                   pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                                   required
                                   minlength="1"
                                   maxlength="80"/>
                            <div class="admin-action-controls">
                                <input class="btn btn-sm btn-secondary bg-success" type="submit" name="update"
                                       value="<fmt:message key="admin.manage.create.btn"/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <c:if test="${requestScope.enableMessage}">
                <div class="admin-operation-message-block">
                    <span class="admin-message"><fmt:message key="${requestScope.resultMessage}"/></span>
                </div>
            </c:if>
        </div>
    </div>
</div>