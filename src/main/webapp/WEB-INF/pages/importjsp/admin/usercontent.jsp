<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
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

