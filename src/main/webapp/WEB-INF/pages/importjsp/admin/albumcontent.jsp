<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-album-management">
    <div class="admin-page-inputs">
        <span class="admin-content-header"><fmt:message key="admin.album.manage.header"/></span>
        <div class="line-separator">
            <hr/>
        </div>
        <div class="album-content-management">
            <div class="album-update-block">
                <h5 style="text-align: center"><fmt:message key="admin.album.manage.update.block.header"/></h5>
                <div class="line-separator">
                    <hr/>
                </div>
                <form class="form-group" action="${pageContext.request.contextPath}/do/admin/album_management/update_album" method="put">
                    <div class="album-select-block">
                        <label for="album-list" style="margin: 0 auto"> <fmt:message key="admin.album.manage.select.caption"/></label>

                        <div class="album-select-block form-group">
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="album-list" name="albumTitle" size="1" required>
                                <option selected value=""></option>
                                <c:forEach var="album" items="${requestScope.albumList}">
                                    <option value="${album.albumTitle}">${album.albumTitle}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="album-update-fields">
                        <div class="album-update-labels">
                            <label for="album-update-name-field"><fmt:message key="admin.album.manage.enter.name.caption"/> </label>
                            <label for="update-singer-list"><fmt:message key="admin.singer.manage.select.caption"/></label>
                        </div>
                        <div class="album-update-inputs form-group">
                            <input class="form-control" id="album-update-name-field"
                                   type="text"
                                   pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                                   required
                                   minlength="1"
                                   maxlength="80"
                                   name="albumUpdateName"/>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="update-singer-list" name="singerName" size="1" required>
                                <option selected value=""></option>
                                <c:forEach var="singer" items="${requestScope.singerList}">
                                    <option value="${singer.singerName}">${singer.singerName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="album-date-block">
                        <div class="album-label-block">
                            <label for="album-update-date-field"><fmt:message key="admin.album.manage.enter.date.caption"/></label>
                        </div>
                        <div class="album-value-block form-group">
                            <input class="form-control" id="album-update-date-field" type="date"
                                   name="albumUpdateDate"
                                   required
                                   style="width: 150px"/>
                        </div>
                    </div>

                    <div class="admin-action-controls">
                        <input class="btn btn-sm btn-secondary bg-warning" type="submit"
                               name="update" value="<fmt:message key="admin.manage.update.btn"/>"/>
                    </div>
                </form>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <div class="album-create-block">
                <h5 style="text-align: center"><fmt:message key="admin.album.manage.create.block.header"/></h5>
                <div class="line-separator">
                    <hr/>
                </div>
                <form class="form-group" action="${pageContext.request.contextPath}/do/admin/album_management/create_album" method="post">
                    <div class="album-update-fields">
                        <div class="album-update-labels">
                            <label for="album-create-name"> <fmt:message key="admin.album.manage.enter.name.caption"/></label>
                            <label for="create-singer-list"><fmt:message key="admin.singer.manage.select.caption"/></label>
                        </div>
                        <div class="album-update-inputs form-group">
                            <input class="form-control" id="album-create-name"
                                   type="text"
                                   pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                                   required
                                   minlength="1"
                                   maxlength="80"
                                   name="albumCreateName"/>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="create-singer-list" name="singerName" size="1" required>
                                <option selected value=""></option>
                                <c:forEach var="singer" items="${requestScope.singerList}">
                                    <option value="${singer.singerName}">${singer.singerName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="album-date-block">
                        <div class="album-label-block">
                            <label for="create-date-field"><fmt:message key="admin.album.manage.enter.date.caption"/></label>
                        </div>
                        <div class="album-value-block form-group">
                            <input class="form-control" id="create-date-field" type="date"
                                   name="albumCreateDate"
                                   required
                                   style="width: 150px"/>
                        </div>
                    </div>
                    <div class="admin-action-controls">
                        <input class="btn btn-sm btn-secondary bg-success" type="submit"
                               name="create" value="<fmt:message key="admin.manage.create.btn"/>"/>
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