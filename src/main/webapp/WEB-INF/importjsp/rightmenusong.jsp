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
        <form action="search">
            <label for="song-name-input" class="search-label search-block-item">
                <fmt:message key="song.serach.input.label"/>
            </label>
            <input class="search-text-input" id="song-name-input" type="text" name="songName"/>
            <div class="search-confirm-input">
                <input type="hidden" name="action" value="searchSong"/>
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                       name="search" value="<fmt:message key="song.search.confirm.btn"/>"/>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <span class="right-menu-label search-block-item">
                    <fmt:message key="song.filter.header"/>
                </span>
            <label for="genre-list" class="search-label search-block-item"><fmt:message key="song.filter.genre.label"/></label>

            <select class="custom-select mr-sm-2" id="genre-list" name="userList" size="1">
                <option value="genreValue" selected></option>
                <c:forEach var="genre" items="${sessionScope.genreList}">
                    <option value="genreValue">${genre.genreName}</option>
                </c:forEach>

            </select>
            <label for="author-list" class="search-label search-block-item"><fmt:message key="song.filter.author.label"/></label>
            <select class="custom-select mr-sm-2" id="author-list" name="userList" size="1">
                <option value="singerValue" selected></option>
                <c:forEach var="singer" items="${sessionScope.singerList}">
                    <option value="singerValue">${singer.singerName}</option>
                </c:forEach>
            </select>

        </form>
        <div class="cancel-filter">
            <a class="btn btn-sm bg-light"
               href="${pageContext.request.contextPath}/mainpage?action=main_page"><fmt:message key="song.filter.reset.btn"/> </a>
        </div>
    </div>
</div>