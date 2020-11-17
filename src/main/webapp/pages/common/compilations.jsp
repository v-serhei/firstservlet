<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/common/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/common/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <c:import url="/WEB-INF/pages/importjsp/common/compilationcontent.jsp"/>
        <c:import url="/WEB-INF/pages/importjsp/common/rightmenucompilation.jsp"/>
    </div>
</div>
<c:import url="/WEB-INF/pages/importjsp/common/footer.jsp"/>
</body>
</html>