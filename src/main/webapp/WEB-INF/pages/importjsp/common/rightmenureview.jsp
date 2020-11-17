<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="right-menu-div">
    <span class="right-menu-label">
        <fmt:message key="review.search.header"/>
    </span>
    <div class="song-searching-controls">
        <form class="form-group" action="${pageContext.request.contextPath}/do/reviews/search" method="get">
            <c:set var="filter" value="${sessionScope.reviewFilter}"/>
            <input type="hidden" name="contentType" value="${filter.contentType}"/>
            <input type="hidden" name="filtered" value="true"/>
            <label for="song-name-input" class="search-label search-block-item">
                <fmt:message key="review.search.input.label"/>
            </label>
            <input class="search-text-input form-control" id="song-name-input"
                   type="text"
                   name="songName"
                   value="${filter.songTitle}"
                   pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{0,80}"/>
            <div class="search-confirm-input">
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                name="search" value="<fmt:message key="search.confirm.btn"/>"/>
            </div>
        </form>
    </div>
    <div class="line-separator">
        <hr/>
    </div>
    <span class="right-menu-label">
        <fmt:message key="review.search.result"/> ${sessionScope.reviewContentCount}
    </span>
    <div class="line-separator">
        <hr/>
    </div>
    <form method="get">
        <input type="hidden" name="filtered" value="false"/>
        <div class="cancel-filter">
            <a class="btn btn-sm bg-light"
            href="${pageContext.request.contextPath}/do/reviews"><fmt:message key="filter.reset.btn"/> </a>
        </div>
    </form>
</div>