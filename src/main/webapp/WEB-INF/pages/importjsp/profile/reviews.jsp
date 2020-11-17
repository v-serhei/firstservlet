<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<c:set var="user" value="${sessionScope.user}"/>
<div class="user-review-list">
    <div class="review-list-header">
        <span class="review-totals">
            <fmt:message key="profile.reviews.header"/> &nbsp; ${requestScope.reviewContentCount}
        </span>
    </div>
    <div class="user-review-items">
        <c:forEach var="review" items="${requestScope.reviewContentList}">
            <div class="line-separator">
                <hr/>
            </div>
            <form action="${pageContext.request.contextPath}/do/reviews/delete_review" method="post">
                <div class="user-reviews-text">
                    <input type="hidden" name="reviewId" value="${review.id}"/>
                    <input class="btn btn-secondary btn-sm bg-danger" style="margin-right: 10px;"
                           type="submit" name="action" value="<fmt:message key="profile.reviews.delete.btn.caption"/>"/>
                    <fmt:message key="profile.reviews.song.caption"/>&nbsp;${review.songTitle}
                    <fmt:message key="profile.reviews.text.caption"/>&nbsp;&nbsp;${review.reviewText}
                </div>
            </form>
        </c:forEach>
    </div>
</div>