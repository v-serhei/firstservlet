<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>;

<div id="top-body-container">
    <div class="top-div-gradient">
    </div>
    <div id="top-menu-div">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.home"/><span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.album"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.compilation"/></a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <div class="btn-group" role="group" aria-label="Basic example">
                    <input type="submit" class="btn btn-secondary"><fmt:message key="main.menu.login"/>
                </div>
            </form>
            <form class="form-inline my-2 my-lg-0">
                <div class="btn-group" role="group" aria-label="Basic example">
                    <input type="submit" class="btn btn-secondary"><fmt:message key="main.menu.registration"/>
                </div>
            </form>
        </nav>
    </div>
</div>