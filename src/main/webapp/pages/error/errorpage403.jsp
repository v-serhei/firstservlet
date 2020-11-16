<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/common/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/common/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div class="error-page-content">
            <h2 class="text-center">Client error!</h2>
            <div class="error-stack-trace">
                <br/>
                <h4 class="text-center"> Received error while processing user request: </h4>
                <h5 class="text-center"> ${pageContext.errorData.requestURI}</h5>
                <br/>
                <h4 class="text-center"> Error code: </h4>
                <h5 class="text-center"> 403</h5>
                <h5 class="text-center"> Requested resource not available or you don't have enough permissions </h5>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</div>
</body>
</html>
