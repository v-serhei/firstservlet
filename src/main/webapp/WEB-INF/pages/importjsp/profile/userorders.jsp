<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<c:set var="user" value="${sessionScope.user}"/>
<div class="user-orders-block">
    <div class="order-block-header">
        <div class="order-list-total">
        <span class="order-totals">
            <fmt:message key="profile.orders.total.orders.count"/>&nbsp; ${requestScope.totalOrderCount}
        </span>
            <span class="order-totals">
            <fmt:message key="profile.orders.total.paid.orders.count"/>&nbsp; ${requestScope.totalPaidOrderCount},
            <fmt:message key="profile.orders.paid.sum"/>&nbsp;${requestScope.totalOrderPrice}
        </span>
        </div>
    </div>

    <div class="user-order-list">
        <c:forEach var="order" items="${requestScope.orderList}">
            <div class="line-separator">
                <hr/>
            </div>
            <div>
            <span style="margin-right: 10px">
               <fmt:message key="profile.orders.order.caption"/>${order.orderId},
                <fmt:message key="profile.orders.price.caption"/> ${order.orderPrice}&nbsp;BYN
                <c:if test="${order.isOrderPaid()}">
                    <fmt:message key="profile.orders.paid.status.caption"/> </span>
                <form action="${pageContext.request.contextPath}/do/profile/download_order" method="post">
                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                    <input type="submit" class="btn btn-secondary btn-sm" name="action"
                           value="<fmt:message key="profile.orders.download.link.caption"/>">
                </form>
                </c:if>
                <c:if test="${!order.isOrderPaid()}">
                    <fmt:message key="profile.orders.not.pdaid.status.caption"/> </span>
                    <form action="${pageContext.request.contextPath}/do/profile/open_order" method="post">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <input type="submit" class="btn btn-secondary btn-sm"
                               name="action" value="<fmt:message key="profile.orders.details.link.caption"/>"/>
                    </form>

                </c:if>
            </div>
        </c:forEach>
    </div>

</div>