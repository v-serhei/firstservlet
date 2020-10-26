<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<%@ taglib prefix="ab" uri="customTagLib" %>

<%--12 штук на страницу--%>

<c:forEach var="song" items="${sessionScope.contentList}">
    <div class="line-separator">
        <hr/>
    </div>
    <div class="item-container">
        <div class="content-values">
            <span class="item-description-song">${song.songTitle}</span>
            <span class="item-description-singer">${song.authorName}</span>
            <span class="item-description-album">${song.albumTitle}</span>
            <span class="item-description-genre">${song.genre}</span>
            <span class="item-description-date">${song.uploadDate}</span>
        </div>
       <%-- <c:if test="${sessionScope.user.loginStatus}">--%>
            <div class="item-controls">
                <a class='btn btn-sm' href='#' title="Добавить в заказ"><img src='${pageContext.request.contextPath}/resources/img/add.png' alt="Добавить в заказ"></a>
                <a class='btn btn-sm' href='#' title="Отзывы"><img src='${pageContext.request.contextPath}/resources/img/review.png' alt="Отзывы"></a>
                <a class='btn btn-sm' href='#' title="Оформить заказ"><img src='${pageContext.request.contextPath}/resources/img/order.png' alt="Оформить заказ"></a>
            </div>
        <%--</c:if>--%>
    </div>
</c:forEach>
<div class="line-separator">
    <hr/>
</div>
<ab:pagination linkValue="${pageContext.request.contextPath}${sessionScope.linkValue}"
               currentPage="${sessionScope.filter.pageNumber}"
               totalPageCount="${sessionScope.totalPageCount}"
/>