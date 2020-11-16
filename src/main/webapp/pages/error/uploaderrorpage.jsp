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
            <h2 class="text-center">Upload Error!</h2>
            <div class="error-stack-trace">
                <br/>
                <h4 class="text-center"> Received error while processing upload file </h4>
                <br/>
                <h5 class="text-center">
                    Server couldn't process uploading file. Possible reasons:
                </h5>
                <br/>
                <h5 class="text-center">
                    Received big file (max file size: 15Mb) or bad request parameters.
                </h5>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</div>
</body>
</html>
