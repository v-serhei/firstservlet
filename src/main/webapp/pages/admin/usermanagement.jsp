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
            <div id="admin-page-main-container">
                <div id="admin-page-blocks">
                    <c:import url="/WEB-INF/pages/importjsp/admin/adminmenu.jsp"/>
                    <c:import url="/WEB-INF/pages/importjsp/admin/usercontent.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</body>
</html>
