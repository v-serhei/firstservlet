<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<%@ taglib prefix="ab" uri="customTagLib" %>

<div id="content-div">
    <div class="review-body-container">
        <div class="review-content">
            <div class="line-separator">
                <hr/>
            </div>
            <div id="accordion">
                <c:set var="step" value="0"/>
                <c:forEach var="song" items="${sessionScope.reviewSongList}">
                    <c:set var="step" value="${step+1}"/>
                    <div class="card">
                        <div class="review-header card-header" style="background-color: whitesmoke">
                            <div class="accordion-inner-header">
                                    <button style="width: 85%" class="btn btn-outline-secondary border-0"
                                            data-toggle="collapse"
                                            data-target="#collapse${step}"
                                            aria-expanded="true"
                                            aria-controls="${step}">
                                            ${song}
                                    </button>
                                    <form action="${pageContext.request.contextPath}/do/review/add_review" method="post">
                                        <input type="hidden" name="songName" value="${song}"/>
                                        <input type="submit" class="btn btn-outline-secondary border-0"
                                               name="action" value="<fmt:message key="review.add.btn"/>">
                                    </form>
                            </div>
                        </div>
                        <div id="collapse${step}"
                             <c:if test="${step==1}">class="collapse show review-collapsed-body"</c:if>
                                <c:if test="${step>1}"> class="collapse review-collapsed-body" </c:if>
                             aria-labelledby="heading${song}"
                             data-parent="#accordion">
                            <div class="card-body">
                                <div class="song-review">
                                    <c:forEach var="review" items="${sessionScope.reviewContentList}">
                                        <c:if test="${review.songTitle==song}">
                                            <span class="review-author">${review.userName}</span>
                                            <div class="review-text">
                                                    ${review.reviewText}
                                            </div>
                                            <div class="line-separator">
                                                <hr/>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div><%--закрыли картд--%>
                    <div class="line-separator">
                        <hr/>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <ab:pagination linkValue="${pageContext.request.contextPath}${sessionScope.reviewLinkValue}"
                   currentPage="${sessionScope.reviewFilter.pageNumber}"
                   totalPageCount="${sessionScope.reviewTotalPageCount}"/>
</div>