<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/common/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/common/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div id="content-div">
            <div id="profile-main-container">
                <div id="profile-blocks">
                    <div id="profile-menu">
            <span id="profile-user-label">
               <h5>${sessionScope.user.userName}</h5>
            </span>
                        <div id="nav-btns">
                            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist"
                                 aria-orientation="vertical">
                                <a class="btn btn-secondary btn-lg btn-block btn-sm"
                                   href="${pageContext.request.contextPath}/do/profile/settings">
                                    <fmt:message key="profile.menu.settings.btn"/>
                                </a>
                                <a class="btn btn-secondary btn-lg btn-block btn-sm"
                                   href="${pageContext.request.contextPath}/do/profile/user_orders">
                                    <fmt:message key="profile.menu.orders.btn"/>
                                </a>
                                <a class="btn btn-secondary btn-lg btn-block btn-sm"
                                   href="${pageContext.request.contextPath}/do/profile/user_reviews">
                                    <fmt:message key="profile.menu.reviews.btn"/>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div id="profile-content">
                        <div class="profile-inputs">
                            <c:import url="/WEB-INF/pages/importjsp/profile/accountsettings.jsp"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</body>
</html>