<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="right-menu-div">
            <span class="right-menu-label">
                <fmt:message key="song.search.header"/>
            </span>
    <div class="song-searching-controls">
        <form action="${pageContext.request.contextPath}/do/main/search" method="get">
            <c:set var="filter" value="${sessionScope.songFilter}"/>
            <input type="hidden" name="contentType" value="${filter.contentType}"/>
            <input type="hidden" name="filtered" value="true"/>
            <label for="song-name-input" class="search-label search-block-item">
                <fmt:message key="song.search.input.label"/>
            </label>
            <input class="search-text-input" id="song-name-input" type="text" name="songName" value="${filter.songTitle}"/>
            <div class="search-confirm-input">
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                       name="search" value="<fmt:message key="search.confirm.btn"/>"/>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <span class="right-menu-label">
                <fmt:message key="song.search.result"/> ${sessionScope.songContentCount}
            </span>
            <div class="line-separator">
                <hr/>
            </div>
            <span class="right-menu-label search-block-item">
                    <fmt:message key="song.filter.header"/>
                </span>
            <label for="genre-list" class="search-label search-block-item"><fmt:message
                    key="song.filter.genre.label"/></label>
            <select class="custom-select mr-sm-2" id="genre-list" name="genreName" size="1">
                <option value="" selected></option>
                <c:forEach var="genre" items="${sessionScope.genreList}">
                    <c:if test="${genre.genreName eq filter.songGenre}">
                        <option selected value="${genre.genreName}">${genre.genreName}</option>
                    </c:if>
                    <option value="${genre.genreName}">${genre.genreName}</option>
                </c:forEach>
            </select>
            <label for="author-list" class="search-label search-block-item"><fmt:message
                    key="song.filter.author.label"/></label>
            <select class="custom-select mr-sm-2" id="author-list" name="singerName" size="1">
                <option value="" selected></option>
                <c:forEach var="singer" items="${sessionScope.singerList}">
                    <c:if test="${singer.singerName eq filter.singerName}">
                        <option selected value="${singer.singerName}">${singer.singerName}</option>
                    </c:if>
                    <option value="${singer.singerName}">${singer.singerName}</option>
                </c:forEach>
            </select>
            <label for="song-name-input" class="search-label search-block-item">
                <fmt:message key="album.search.input.label"/>
            </label>
            <input class="search-text-input" id="song-name-input" type="text" name="albumTitle" value="${filter.albumTitle}"/>
            <div class="search-confirm-input">
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                       name="search" value="<fmt:message key="search.confirm.btn"/>"/>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
        </form>
        <form>
            <input type="hidden" name="filtered" value="false"/>
            <div class="cancel-filter">
                <a class="btn btn-sm bg-light"
                   href="${pageContext.request.contextPath}/do/main"><fmt:message
                        key="filter.reset.btn"/> </a>
            </div>
        </form>

    </div>
</div>