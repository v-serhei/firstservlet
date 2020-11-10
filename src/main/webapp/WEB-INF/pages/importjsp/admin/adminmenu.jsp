<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<c:set var="user" value="${sessionScope.user}"/>

<div id="admin-page-menu">
            <span id="admin-page-user-label">
               <h5>Username</h5>
            </span>
    <div id="nav-btns">
        <div id="admin-menu">
            <div class="form-group">
                <span id="admin-menu-label">Administrator menu</span>
                <div id="list-example" class="list-group">
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="admin-user-menu"
                            onclick="hideContent(this.id,'admin-page-user-management')">Users management
                    </button>
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="admin-review-menu"
                            onclick="hideContent(this.id,'admin-page-review-management')">reviews management
                    </button>
                </div>
            </div>
        </div>
        <div id="admin-content-menu">
            <div class="form-group">
                <span id="admin-content-menu-label">Content menu</span>
                <div id="list-example2" class="list-group">
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="content1"
                            onclick="hideContent(this.id,'admin-page-track-management')">Tracks
                    </button>
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="content2"
                            onclick="hideContent(this.id,'admin-page-performer-management')">Performers
                    </button>
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="content3"
                            onclick="hideContent(this.id,'admin-page-genre-management')">Genres
                    </button>
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="content4"
                            onclick="hideContent(this.id,'admin-page-albums-management')">Albums
                    </button>
                    <button class="btn btn-secondary btn-lg btn-block btn-sm bg-secondary btn-admin-menu"
                            id="content5"
                            onclick="hideContent(this.id,'admin-page-compilations-management')">Compilations
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>