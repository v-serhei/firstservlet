<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-user-management">
    <div class="admin-page-inputs" id="admin-page-account-settings">
        <div id="users-account-settings-content">

            <span class="admin-content-header"><fmt:message key="admin.user.manage.header"/></span>
            <div class="line-separator">
                <hr/>
            </div>
            <div id="select-user-block">
                <form action="${pageContext.request.contextPath}/do/admin/user_management" method="post">
                    <label for="user-list" id="user-list-label"> <fmt:message
                            key="admin.user.manage.userlist.caption"/></label>
                    <select class="custom-select mr-sm-2" id="user-list" name="selectedUser" size="1">
                        <option value="" selected></option>
                        <c:if test="${requestScope.userFlag}">
                            <c:set var="selectedUser" value="${requestScope.selectedUser}"/>

                            <c:forEach var="user" items="${requestScope.userList}">
                                <c:if test="${user.userName eq selectedUser.userName}">
                                    <option selected value="${user.userName}">${user.userName}</option>
                                </c:if>
                                <c:if test="${!(user.userName eq selectedUser.userName)}">
                                    <option value="${user.userName}">${user.userName}</option>
                                </c:if>
                            </c:forEach>

                        </c:if>

                        <c:if test="${!requestScope.userFlag}">
                            <c:forEach var="user" items="${requestScope.userList}">
                                <option value="${user.userName}">${user.userName}</option>
                            </c:forEach>
                        </c:if>

                    </select>
                    <input type="submit" class="btn btn-secondary" name="findUser"
                           value="<fmt:message key="admin.user.manage.select.btn"/>"/>
                </form>
            </div>
            <div class="line-separator">
                <hr/>
            </div>

            <c:if test="${requestScope.userFlag}">
                <div class="account-options" id="user-description-block">
                    <span id="user-details-table-caption"><fmt:message
                            key="admin.user.manage.description.header"/></span>
                    <table class="user-details-table">
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message
                                        key="admin.user.manage.description.reg.date"/></span>
                            </td>
                            <td>
                                <span class="item-value"> ${selectedUser.registrationDate}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message key="admin.user.manage.description.email"/></span>
                            </td>
                            <td>
                                <span class="item-value"> ${selectedUser.email}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message key="admin.user.manage.description.role"/></span>
                            </td>
                            <td>
                                <span class="item-value"> ${selectedUser.roleDescription}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message
                                        key="admin.user.manage.description.blocked.status"/></span>
                            </td>
                            <td>
                                <c:if test="${selectedUser.blockedStatus}">
                                    <span class="item-value"> <fmt:message key="admin.user.manage.description.blocked.status.true"/> </span>
                                </c:if>
                                <c:if test="${!selectedUser.blockedStatus}">
                                    <span class="item-value"> <fmt:message key="admin.user.manage.description.blocked.status.false"/></span>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message
                                        key="admin.user.manage.description.discount"/></span>
                            </td>
                            <td>
                                <span class="item-value"> ${selectedUser.discount}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message
                                        key="admin.user.manage.description.total.order"/></span></td>
                            <td>
                                <span class="item-value"> ${requestScope.totalPaidOrderCount}</span>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="user-item"><fmt:message
                                        key="admin.user.manage.description.total.sum"/></span>
                            </td>
                            <td>
                                <span class="item-value">${requestScope.totalOrderPrice} </span>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="line-separator">
                    <hr/>
                </div>
                <form action="${pageContext.request.contextPath}/do/admin/update_user" method="put">
                    <input type="hidden" name="fieldName" value="${selectedUser.userName}">
                    <div class="account-options">
                        <label for="user-role"><fmt:message key="admin.user.manage.change.role.caption"/></label>
                        <select class="custom-select mr-sm-2" id="user-role" name="userRole" size="1">
                            <c:forEach var="role" items="${requestScope.roleList}">
                                <c:if test="${selectedUser.roleDescription eq role}">
                                    <option selected value="${role}">${role}</option>
                                </c:if>
                                <c:if test="${!(selectedUser.roleDescription eq role)}">
                                    <option value="${role}">${role}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="account-options">
                        <label for="user-discount"> <fmt:message key="admin.user.manage.change.discount.caption"/> </label>
                        <select class="custom-select mr-sm-2" id="user-discount" name="userDiscount"
                                size="1">
                            <option value="0">0</option>
                            <option value="3">3</option>
                            <option value="5">5</option>
                            <option value="7">7</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                            <option value="25">25</option>
                            <option value="30">30</option>
                        </select>
                    </div>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="account-options">
                        <label for="user-status"> <fmt:message key="admin.user.manage.change.status"/></label>
                        <select class="custom-select mr-sm-2" id="user-status" name="userStatus" size="1">
                            <option selected value="active">Active</option>
                            <option value="blocked">Blocked</option>
                        </select>
                    </div>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <div class="account-options" id="admin-save-user-settings">
                        <input type="submit" class="btn btn-light btn-sm"
                               name="action" value="<fmt:message key="admin.user.manage.update.btn"/>">
                    </div>
                </form>
            </c:if>
            <c:if test="${requestScope.enableMessage}">
                <div class="admin-operation-message-block">
                    <span class="admin-message"><fmt:message key="${requestScope.resultMessage}"/></span>
                </div>
            </c:if>
        </div>
    </div>
</div>