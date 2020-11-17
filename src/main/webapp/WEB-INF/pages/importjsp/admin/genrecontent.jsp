<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-genre-management">
    <div class="admin-page-inputs">
        <span class="admin-content-header"><fmt:message key="admin.genre.manage.header"/></span>
        <div class="line-separator">
            <hr/>
        </div>
        <div class="genre-content-management">
            <div class="genre-update-block">
                <h5 style="text-align: center"><fmt:message key="admin.genre.manage.update.block.header"/></h5>
                <div class="line-separator">
                    <hr/>
                </div>
                <label for="genre-list" style="margin: 0 auto"> <fmt:message key="admin.genre.manage.select.caption"/> </label>
                <form class="form-group" action="${pageContext.request.contextPath}/do/admin/genre_management/update_genre" method="put">
                    <div class="genre-select-block form-group">
                        <select class="custom-select mr-sm-2 admin-item-selector"
                                id="genre-list" name="genreName" size="1" required>
                            <option selected value=""></option>
                            <c:forEach var="genre" items="${requestScope.genreList}">
                                <option value="${genre.genreName}">${genre.genreName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="genre-update-inputs form-group">
                        <label for="genre-update-name"> <fmt:message
                                key="admin.genre.manage.enter.name.caption"/></label>
                        <input class="form-control" id="genre-update-name"
                               type="text"
                               name="genreUpdateName"
                               pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
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
            <h5 style="text-align: center"><fmt:message key="admin.genre.manage.create.block.header"/></h5>
            <div class="line-separator">
                <hr/>
            </div>
            <div class="genre-create-block">
                <div class="genre-update-block">
                    <div class="genre-create-inputs">
                        <form class="form-group" action="${pageContext.request.contextPath}/do/admin/genre_management/create_genre" method="post">
                            <label for="genre-create-name" style="margin: 0 auto"><fmt:message
                                    key="admin.genre.manage.enter.name.caption"/></label>
                            <input class="form-control" id="genre-create-name"
                                   type="text"
                                   name="genreCreateName"
                                   pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                                   required
                                   minlength="1"
                                   maxlength="80"/>
                            <div class="admin-action-controls">
                                <input class="btn btn-sm btn-secondary bg-success" type="submit" name="create"
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