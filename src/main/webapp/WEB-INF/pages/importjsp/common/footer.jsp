<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="bottom-body-container">
    <div id="footer-container">
        <footer class="f">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
                <div id="footer-div">
                    <span class="navbar-text" id="footer-span"> <fmt:message key="footer.message"/></span>
                </div>
                <div id="lang-menu" style="display: flex">
                    <form action="${pageContext.request.contextPath}/do/language/en" method="post">
                        <input type="hidden" name="currentPageUri" value="${sessionScope.lastUri}">
                        <div class="btn-group btn-group-sm" role="group" aria-label="Button group with nested dropdown">
                            <button type="submit" class="btn btn-secondary" name="language" value="en">En</button>
                        </div>
                    </form>
                    <form action="${pageContext.request.contextPath}/do/language/ru" method="post">
                        <input type="hidden" name="currentPageUri" value="${sessionScope.lastUri}">
                        <div class="btn-group btn-group-sm" role="group" aria-label="Button group with nested dropdown">
                            <button type="submit" class="btn btn-secondary" name="language" value="ru">Ru</button>
                        </div>
                    </form>
                </div>
            </nav>
        </footer>
    </div>
</div>
<div class="bottom-div-gradient">
</div>
