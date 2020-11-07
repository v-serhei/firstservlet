<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/header.jsp"/>
<body>
<div id="top-body-container">
    <div class="top-div-gradient">
    </div>
    <div id="top-menu-div">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#"><fmt:message key="main.menu.home"/></a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/profile.js"></script>
<div id="main-body-container">
    <div id="body-container">
        <div id="content-div">
            <div id="profile-main-container">
                <div id="profile-blocks">
                    <div id="profile-menu">
            <span id="profile-user-label">
               <h5>Username</h5>
            </span>
                        <div id="nav-btns">
                            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist"
                                 aria-orientation="vertical">
                                <button class="btn btn-secondary btn-lg btn-block btn-sm" id="user-settings"
                                        onclick="showSettingsContent()">Settings
                                </button>
                                <button class="btn btn-secondary btn-lg btn-block btn-sm" id="user-orders"
                                        onclick="showOrderContent()">orders
                                </button>
                                <button class="btn btn-secondary btn-lg btn-block btn-sm" id="user-reviews"
                                        onclick="showReviewContent()">reviews
                                </button>
                            </div>
                        </div>
                    </div>
                    <div id="profile-content">
                        <div class="profile-inputs" id="profile-account-settings">
                            account block
                        </div>
                        <div class="profile-inputs" id="profile-order-block">
                            orders block
                        </div>
                        <div class="profile-inputs" id="profile-review-block">
                            reviews block
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/footer.jsp"/>
</body>
</html>