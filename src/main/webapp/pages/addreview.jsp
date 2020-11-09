<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<!DOCTYPE html>
<html>
<c:import url="/WEB-INF/pages/importjsp/header.jsp"/>
<body>
<c:import url="/WEB-INF/pages/importjsp/topmenu.jsp"/>
<div id="main-body-container">
    <div id="body-container">
        <div class="create-review-block">
            <form action="${pageContext.request.contextPath}/do/review/create_review" method="post">
                <div class="create-review-header">
                    <span><fmt:message key="review.add.header"/></span>
                    <span><fmt:message key="review.add.song.caption"/></span>
                    <label>
                        <input class="form-control review-readonly-text" type="text" name="songName"
                               value="${sessionScope.song}" readonly>
                    </label>
                    <span><fmt:message key="review.add.author.caption"/></span>
                    <label>
                        <input class="form-control review-readonly-text" type="text" name="singerName"
                               value="${sessionScope.singer}" readonly>
                    </label>
                </div>
                <div class="create-review-body">
                    <label for="reviewText"><fmt:message key="review.add.text.label"/></label>
                    <textarea class="form-control" id="reviewText" name="reviewText" rows="12" cols="70"></textarea>
                    <div class="create-review-controls">
                        <input type="submit" class="btn btn-sm btn-secondary"
                               name="action" value="<fmt:message key="review.create.btn"/>"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/pages/importjsp/footer.jsp"/>
</div>
</body>
</html>
