<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<%@ taglib prefix="ab" uri="customTagLib" %>

<%--12 штук на страницу--%>
<div id="content-div">
    <c:forEach var="song" items="${sessionScope.songContentList}">
        <div class="line-separator">
            <hr/>
        </div>
        <div class="item-container">
            <div class="content-values">
                <span class="item-description-song">${song.songTitle}</span>
                <span class="item-description-singer">${song.authorName}</span>
                <div class="album-title-description">
                    <span class="item-description-album">${song.albumTitle}</span>
                    <span class="item-description-album-date">${song.albumCreationDate}</span>
                </div>
                <span class="item-description-genre">${song.genre}</span>
                <div class="upload-description">
                    <span class="item-description-upload"><fmt:message key="song.description.upload.header"/></span>
                    <span class="item-description-upload-date">${song.uploadDate}</span>
                </div>

                <span class="item-description-price">${song.price}</span>
                <span class="item-description-price-currency">BYN</span>

            </div>
            <div class="item-controls">

                    <c:if test="${sessionScope.user.basket.contains(song.id)}">
                    <form action="${pageContext.request.contextPath}/do/basket_remove" method="get">
                        <input type="hidden" name="currentPageUri" value="${sessionScope.lastUri}">
                        <input type="hidden" name="orderedSongId" value="${song.id}">
                        <input type="submit" class="btn btn-sm song-content-controls"
                               style="background-image: url('${pageContext.request.contextPath}/resources/img/remove.png')"
                               alt="Add to order" value=""
                               data-toggle="tooltip" data-placement="top"
                               title="<fmt:message key="song.controls.hint.remove"/>">
                    </form>
                    </c:if>
                    <c:if test="${!sessionScope.user.basket.contains(song.id)}">
                <form action="${pageContext.request.contextPath}/do/basket_add" method="get">
                        <input type="hidden" name="currentPageUri" value="${sessionScope.lastUri}">
                        <input type="hidden" name="orderedSongId" value="${song.id}">
                        <input type="submit" class="btn btn-sm song-content-controls"
                               style="background-image: url('${pageContext.request.contextPath}/resources/img/add.png')"
                               alt="Add to order" value=""
                               data-toggle="tooltip" data-placement="top"
                               title="<fmt:message key="song.controls.hint.add"/>">
                </form>
                    </c:if>

                <form action="${pageContext.request.contextPath}/do/reviews" method="get">
                    <input type="hidden" name="songName" value="${song.songTitle}">
                    <input type="submit" class="btn btn-sm song-content-controls"
                           style="background-image: url('${pageContext.request.contextPath}/resources/img/review.png')"
                           alt="View reviews" value=""
                           data-toggle="tooltip" data-placement="top"
                           title="<fmt:message key="song.controls.hint.get.review"/>">
                </form>
                <form action="${pageContext.request.contextPath}/do/create_order" method="get">
                    <input type="submit" class="btn btn-sm song-content-controls"
                           style="background-image: url('${pageContext.request.contextPath}/resources/img/order.png')"
                           alt="Create order" value=""
                           data-toggle="tooltip" data-placement="top"
                           title="<fmt:message key="song.controls.hint.go.order"/>">
                </form>
            </div>
        </div>
    </c:forEach>
    <div class="line-separator">
        <hr/>
    </div>
    <ab:pagination linkValue="${pageContext.request.contextPath}${sessionScope.songLinkValue}"
                   currentPage="${sessionScope.songFilter.pageNumber}"
                   totalPageCount="${sessionScope.songTotalPageCount}"
    />
</div>