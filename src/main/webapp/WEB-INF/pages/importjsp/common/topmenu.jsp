<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="top-body-container">
    <div class="top-div-gradient">
    </div>
    <div id="top-menu-div">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/do/main"><fmt:message key="main.menu.home"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/do/compilations"><fmt:message key="main.menu.compilation"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/do/reviews"><fmt:message key="review.page.btn"/></a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0" method="get">
                <div class="btn-group btn-group-sm" role="group">
                    <c:choose>
                        <c:when test="${sessionScope.user.loginStatus}">
                            <!-- if logged in -->
                            <button type="button" class="btn btn-secondary btn-lg" disabled><fmt:message
                                    key="user.menu.greetings"/></button>
                            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                    ${sessionScope.user.userName}!
                            </button>
                            <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/do/profile/settings"><fmt:message key="user.menu.profile"/></a>
                                <c:if test="${sessionScope.user.adminRole}">
                                    <div class="line-separator">
                                        <hr/>
                                    </div>
                                    <a class="dropdown-item bg-light" href="${pageContext.request.contextPath}/do/admin/user_management"><fmt:message key="user.menu.adminpage"/></a>
                                </c:if>
                                <div class="line-separator">
                                    <hr/>
                                </div>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/do/process_logout"><fmt:message key="user.menu.logout"/> </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- if not logged in -->
                            <button type="button" class="btn btn-secondary btn-lg" disabled><fmt:message
                                    key="user.menu.greetings"/></button>
                            <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <fmt:message key="user.menu.default.name"/>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/do/login"><fmt:message
                                        key="user.menu.login"/></a>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/do/registration"><fmt:message
                                        key="user.menu.registration"/></a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </nav>
    </div>
</div>