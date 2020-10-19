<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/importjsp/header.jsp"/>
<body>
<c:import url="../../WEB-INF/importjsp/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div class="error-page-content">
            <h2 class="text-center">Server Error!</h2>
            <div class="error-stack-trace">
                <br/>
                <h4 class="text-center"> Received exception while processing user request: </h4>
                <h5 class="text-center"> ${pageContext.errorData.requestURI}</h5>
                <br/>
                <h4 class="text-center"> Error code: </h4>
                <h5 class="text-center"> ${pageContext.errorData.statusCode}</h5>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/importjsp/footer.jsp"/>
</div>
</body>
</html>
