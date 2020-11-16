<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div class="admin-page-content" id="admin-page-song-management">
    <div class="admin-page-inputs" id="admin-page-account-settings">
        <span class="admin-content-header"><fmt:message key="admin.song.manage.header"/></span>
        <div class="line-separator">
            <hr/>
        </div>

        <c:set var="songList" value="${requestScope.songList}"/>
        <c:set var="singerList" value="${requestScope.singerList}"/>
        <c:set var="genreList" value="${requestScope.genreList}"/>
        <c:set var="albumList" value="${requestScope.albumList}"/>

        <div class="song-content-management">
            <div class="song-management-block">
                <div class="song-update-block">
                    <h5 style="text-align: center"><fmt:message key="admin.song.manage.update.block.header"/></h5>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <form action="${pageContext.request.contextPath}/do/admin/song_management/update_song" method="put">
                        <div class="song-selector">
                            <label for="song-list"><fmt:message key="admin.song.manage.select.caption"/></label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="song-list" name="songId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="song" items="${songList}">
                                    <option value="${song.id}">${song.songMergedTitle}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="line-separator">
                            <hr/>
                        </div>

                        <div class="song-title-block">
                            <label for="song-update-name"><fmt:message key="admin.song.manage.enter.title.caption"/></label>
                            <input class="form-control" id="song-update-name" type="text"
                                   name="updatedSongName">
                        </div>

                        <div class="singer-select-block">
                            <label for="singer-list" style="margin: 0 auto"> <fmt:message
                                    key="admin.singer.manage.select.caption"/> </label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="singer-list" name="singerId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="singer" items="${singerList}">
                                    <option value="${singer.id}">${singer.singerName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="album-select-block">
                            <label for="album-list" style="margin: 0 auto"><fmt:message
                                    key="admin.album.manage.select.caption"/></label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="album-list" name="albumId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="album" items="${albumList}">
                                    <option value="${album.id}">${album.mergedTitle}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="genre-select-block">
                            <label for="genre-list" style="margin: 0 auto"> <fmt:message
                                    key="admin.genre.manage.select.caption"/> </label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="genre-list" name="genreId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="genre" items="${genreList}">
                                    <option value="${genre.id}">${genre.genreName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="song-price-block">
                            <label for="song-price"><fmt:message key="admin.song.manage.enter.price.caption"/></label>
                            <input class="form-control" id="song-price" type="text"
                                   name="songPrice">
                        </div>

                        <div class="singer-update-inputs">
                            <div class="admin-action-controls">
                                <input class="btn btn-sm btn-secondary bg-warning" type="submit" name="update"
                                       value="<fmt:message key="profile.settings.save.btn"/>">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="song-create-block">
                    <h5 style="text-align: center"><fmt:message key="admin.song.manage.create.block.header"/></h5>
                    <div class="line-separator">
                        <hr/>
                    </div>
                    <form action="${pageContext.request.contextPath}/process/upload" enctype="multipart/form-data"
                          method="post">
                        <div class="song-title-block">
                            <label for="song-create-name"><fmt:message key="admin.song.manage.enter.title.caption"/></label>
                            <input class="form-control" id="song-create-name" type="text"
                                   name="songName">
                        </div>

                        <div class="singer-select-block">
                            <label for="singer-list2" style="margin: 0 auto"> <fmt:message
                                    key="admin.singer.manage.select.caption"/> </label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="singer-list2" name="singerId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="singer" items="${singerList}">
                                    <option value="${singer.id}">${singer.singerName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="album-select-block">
                            <label for="album-list2" style="margin: 0 auto"> <fmt:message
                                    key="admin.album.manage.select.caption"/> </label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="album-list2" name="albumId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="album" items="${albumList}">
                                    <option value="${album.id}">${album.mergedTitle}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="genre-select-block">
                            <label for="genre-list2" style="margin: 0 auto"> <fmt:message
                                    key="admin.genre.manage.select.caption"/></label>
                            <select class="custom-select mr-sm-2 admin-item-selector"
                                    id="genre-list2" name="genreId" size="1">
                                <option selected value=""></option>
                                <c:forEach var="genre" items="${genreList}">
                                    <option value="${genre.id}">${genre.genreName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="song-price-block">
                            <label for="song-price2"><fmt:message key="admin.song.manage.enter.price.caption"/></label>
                            <input class="form-control" id="song-price2" type="text"
                                   name="songPrice">
                        </div>

                        <div class="upload-song-block">
                            <h5><fmt:message key="admin.song.manage.choose.file.header"/></h5>
                            <div class="input__wrapper">
                                <input type="file" name="fileName" id="input__file" class="input input__file"
                                       accept="audio/*">
                                <label for="input__file" class="input__file-button">
                                <span class="input__file-button-text"><fmt:message
                                        key="admin.song.manage.choose.file.input.caption"/></span>
                                </label>
                            </div>
                        </div>


                        <div class="singer-update-inputs">

                            <div class="admin-action-controls">
                                <input class="btn btn-sm btn-secondary bg-success" type="submit" name="action"
                                       value="Create">
                            </div>

                        </div>
                    </form>

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