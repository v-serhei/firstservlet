<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<%@ taglib prefix="ab" uri="customTagLib" %>

<%--12 штук на страницу--%>
<div class="order-content-block">
    <c:set var="currentOrder" value="${requestScope.requestedOrder}"/>
    <span class="order-header"><fmt:message key="order.number.caption"/> &nbsp; ${currentOrder.orderId} </span>
    <div class="order-description-block">
        <div class="order-items">
            <c:forEach var="song" items="${currentOrder.orderList}">
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
                            <span class="item-description-upload"><fmt:message
                                    key="song.description.upload.header"/></span>
                            <span class="item-description-upload-date">${song.uploadDate}</span>
                        </div>
                        <span class="item-description-price">${song.price}</span>
                        <span class="item-description-price-currency">BYN</span>
                    </div>
                    <c:if test="${requestScope.enableOrderControls}">
                    <div class="item-controls">
                        <form action="${pageContext.request.contextPath}/do/order/song_remove" method="get">
                            <input type="hidden" name="orderedSongId" value="${song.id}">
                            <input type="hidden" name="orderId" value="${currentOrder.orderId}">
                            <input type="submit" class="btn btn-sm song-content-controls"
                                   style="background-image: url('${pageContext.request.contextPath}/resources/img/remove.png')"
                                   alt="Remove" value=""
                                   data-toggle="tooltip" data-placement="top"
                                   title="<fmt:message key="operation.order.remove.song.controls.hint"/>">
                        </form>
                    </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

        <div class="order-result">
            <span class="order-count-result"> <fmt:message key="order.total.count.caption"/>&nbsp;${currentOrder.orderList.size()}</span>
            <span class="order-cash-result"> <fmt:message key="order.total.cash"/>&nbsp;${currentOrder.orderPrice}</span>
        </div>
        <c:if test="${requestScope.enableOrderControls}">
            <div class="order-controls">
                <form action="${pageContext.request.contextPath}/do/pay_order">
                    <input type="hidden" name="orderId" value="${currentOrder.orderId}">
                    <input type="submit" class="btn btn-sm btn-secondary bg-success"
                           name="pay" value="<fmt:message key="order.confirm.btn"/>">
                </form>
                <form action="${pageContext.request.contextPath}/do/remove_order">
                    <input type="hidden" name="orderId" value="${currentOrder.orderId}">
                    <input type="submit" class="btn btn-sm btn-secondary" style="margin-left: 30px"
                           name="pay" value="<fmt:message key="order.delete.btn"/>">
                </form>
            </div>
        </c:if>
    </div>
</div>