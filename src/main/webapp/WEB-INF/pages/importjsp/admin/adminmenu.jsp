<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="admin-page-menu">
            <span id="admin-page-user-label">
               <h5>${sessionScope.user.userName}</h5>
            </span>
    <div id="nav-btns">
        <div id="admin-menu">
            <div class="form-group">
                <span id="admin-menu-label"><fmt:message key="admin.menu.label"/></span>
                <div id="list-example" class="list-group">
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/user_management">
                        <fmt:message key="admin.menu.user.management.btn"/>
                    </a>
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/review_management">
                        <fmt:message key="admin.menu.review.btn"/>
                    </a>
                </div>
            </div>
        </div>
        <div id="admin-content-menu">
            <div class="form-group">
                <span id="admin-content-menu-label"><fmt:message key="admin.content.menu.label"/></span>
                <div id="list-example2" class="list-group">
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/song_management">
                        <fmt:message key="admin.content.menu.track.menu.btn"/>
                    </a>
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/singer_management">
                        <fmt:message key="admin.content.menu.artist.menu.btn"/>
                    </a>
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/genre_management">
                        <fmt:message key="admin.content.menu.genre.menu.btn"/>
                    </a>
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/album_management">
                        <fmt:message key="admin.content.menu.album.menu.btn"/>
                    </a>
                    <a class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                       href="${pageContext.request.contextPath}/do/admin/compilation_management">
                        <fmt:message key="admin.content.menu.compilation.menu.btn"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>