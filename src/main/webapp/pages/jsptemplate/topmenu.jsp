<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <a class="nav-link" href="#"><fmt:message key="main.menu.home"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.album"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.compilation"/></a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <div class="btn-group btn-group-sm" role="group">
                    <button type="button" class="btn btn-secondary btn-lg" disabled><fmt:message key="user.menu.greetings"/></button>
                    <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                       user
                    </button>
                    <div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/login"><fmt:message key="user.menu.login"/></a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/registration"><fmt:message key="user.menu.registration"/></a>
                    </div>
                </div>
            </form>
        </nav>
    </div>
</div>