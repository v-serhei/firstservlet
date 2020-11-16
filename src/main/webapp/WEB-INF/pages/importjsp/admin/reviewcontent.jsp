<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-review-management">
    <div class="admin-page-inputs">
        <div class="profile-inputs">

            <span class="admin-content-header"><fmt:message key="admin.review.manage.header"/></span>
            <div class="line-separator">
                <hr/>
            </div>
            <div id="select-user-block">
                <form action="${pageContext.request.contextPath}/do/admin/review_management" method="post">
                    <label for="user-list" id="user-list-label">
                        <fmt:message key="admin.user.manage.userlist.caption"/></label>
                    <select class="custom-select mr-sm-2" id="user-list" name="selectedUser" size="1">
                        <option value="" selected></option>
                        <c:set var="selectedUser" value="${requestScope.selectedUser}"/>
                        <c:forEach var="user" items="${requestScope.userList}">
                            <c:if test="${user.userName eq selectedUser.userName}">
                                <option selected value="${user.userName}">${user.userName}</option>
                            </c:if>
                            <c:if test="${!(user.userName eq selectedUser.userName)}">
                                <option value="${user.userName}">${user.userName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input type="submit" class="btn btn-secondary" name="findUser"
                           value="<fmt:message key="admin.user.manage.select.btn"/>"/>
                </form>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <div class="review-list-header">
                <span class="review-totals" ><fmt:message key="admin.review.manage.total.review.count"/> &nbsp; ${requestScope.reviewContentCount}</span>
            </div>
            <div class="user-review-list">
                <div class="admin-content-review-items">
                    <c:forEach var="review" items="${requestScope.reviewContentList}">
                        <div class="line-separator">
                            <hr/>
                        </div>
                        <form action="${pageContext.request.contextPath}/do/reviews/delete_user_review" method="delete">
                            <div class="user-reviews-text">
                                <input type="hidden" name="reviewId" value="${review.id}"/>
                                <input class="btn btn-secondary btn-sm bg-danger" style="margin-right: 10px;"
                                       type="submit" name="action"
                                       value="<fmt:message key="profile.reviews.delete.btn.caption"/>"/>
                                <fmt:message key="profile.reviews.song.caption"/>&nbsp;${review.songTitle}
                                <fmt:message key="profile.reviews.text.caption"/>&nbsp;&nbsp;${review.reviewText}
                            </div>
                        </form>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${requestScope.enableMessage}">
                <div class="admin-operation-message-block">
                    <span class="admin-message"><fmt:message key="${requestScope.resultMessage}"/></span>
                </div>
            </c:if>
        </div>
    </div>
</div>