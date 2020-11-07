<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/topmenu.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/adminka.js"></script>

<div id="main-body-container">
    <div id="body-container">
        <div id="content-div">
            <div id="admin-page-main-container">
                <div id="admin-page-blocks">
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
                                                id="admin-order-menu"
                                                onclick="hideContent(this.id,'admin-page-order-management')">orders management
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
                    <div class="admin-page-content" id="admin-page-user-management">
                        <div class="admin-page-inputs" id="admin-page-account-settings">
                            <div id="users-account-settings-content">

                                <form action="mainpage" method="post">
                                    <span class="admin-content-header">User management page</span>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div id="select-user-block">

                                        <label for="user-list" id="user-list-label"> Select user from list </label>
                                        <select class="custom-select mr-sm-2" id="user-list" name="userList" size="1">
                                            <option value="username1">username1</option>
                                            <option value="username1">username2</option>
                                            <option value="username1">username3</option>
                                            <option value="username1">username41111111111111</option>
                                        </select>
                                    </div>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div class="account-options" id="user-description-block">
                                        <span id="user-details-table-caption">User details:</span>
                                        <table id="user-details-table">
                                            <tr>
                                                <td>
                                                    <span class="user-item">registration date</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">e-mail</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">role date</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">status</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">personal discount</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">total orders count</span></td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="user-item">total orders summ</span>
                                                </td>
                                                <td>
                                                    <span class="item-value"> value</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div class="account-options">
                                        <label for="user-role"> Change role </label>
                                        <select class="custom-select mr-sm-2" id="user-role" name="userRole" size="1">
                                            <option value="admin">Admin</option>
                                            <option value="reg">reg user</option>
                                        </select>
                                    </div>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div class="account-options">
                                        <label for="user-discount"> Change personal discount </label>
                                        <select class="custom-select mr-sm-2" id="user-discount" name="userRole"
                                                size="1">
                                            <option value="val0">0</option>
                                            <option value="val5">5</option>
                                        </select>
                                    </div>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div class="account-options">
                                        <label for="user-status"> Change blocked status </label>
                                        <select class="custom-select mr-sm-2" id="user-status" name="userRole" size="1">
                                            <option value="val0">Blocked</option>
                                            <option value="val5">Active</option>
                                        </select>
                                    </div>
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <div class="account-options" id="admin-save-user-settings">
                                        <button type="submit" class="btn btn-light btn-sm" name="action" value="main_page">Save
                                            changes
                                        </button>
                                    </div>

                                    <div class="admin-operation-message-block">
                                        <span class="admin-message">Мессага об успешности выполненной операции</span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="admin-page-content" id="admin-page-order-management">
                        orders settings
                    </div>
                    <div class="admin-page-content" id="admin-page-review-management">
                        review settings
                    </div>
                    <div class="admin-page-content" id="admin-page-track-management">
                        track settings
                    </div>
                    <div class="admin-page-content" id="admin-page-performer-management">
                        performer settings
                    </div>
                    <div class="admin-page-content" id="admin-page-genre-management">
                        genre settings
                    </div>
                    <div class="admin-page-content" id="admin-page-albums-management">
                        albums settings
                    </div>
                    <div class="admin-page-content" id="admin-page-compilations-management">
                        compilations settings
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/footer.jsp"/>
</body>
</html>
