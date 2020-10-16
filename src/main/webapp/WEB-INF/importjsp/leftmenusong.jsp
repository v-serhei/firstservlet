<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="right-menu-div">
            <span class="right-menu-label">
                Поиск
            </span>
    <div id="accordion-song-search">

        <div class="card" id="song-name-filter-controls">
            <div class="card-header" id="headingOne">
                <h5 class="mb-0">
                    <button class="accordion-selector btn btn-secondary btn-sm" data-toggle="collapse"
                            data-target="#song-name-filter" aria-expanded="true"
                            aria-controls="song-name-filter">
                        Название
                    </button>
                </h5>
            </div>

            <div id="song-name-filter" class="collapse show" aria-labelledby="headingOne"
                 data-parent="#accordion-song-search">
                <div class="card-body">
                    <label for="song-name-field" class="filter-label">Название содержит </label>
                        <input id="song-name-field" type="text"/>

                    <div class="filter-apply-controls">
                        <input type="hidden" name="action" value="findSongByName"/>
                        <input type="submit" class="btn btn-sm bg-white" name="apply" value="Find"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <span class="right-menu-label">Фильтры: </span>

    <div id="accordion-song-filter">
        <div class="card" id="song-genre-filter-controls">
            <div class="card-header" id="headingTwo">
                <h5 class="mb-0">
                    <button class="accordion-selector btn btn-secondary btn-sm" data-toggle="collapse"
                            data-target="#song-genre-filter" aria-expanded="true"
                            aria-controls="song-genre-filter">
                        Жанр
                    </button>
                </h5>
            </div>

            <div id="song-genre-filter" class="collapse" aria-labelledby="headingOne"
                 data-parent="#accordion-song-filter">
                <div class="card-body">
                    <label for="genre-list" class="filter-label"> Выберите жанр из списка</label>
                    <select class="custom-select mr-sm-2" id="genre-list" name="userList" size="1">
                        <option value="username1"></option>
                        <option value="username1">Жанр1</option>
                        <option value="username1">Жанр2</option>
                        <option value="username1">Жанр3</option>
                    </select>

                    <div class="filter-apply-controls">
                        <input type="hidden" name="action" value=""/>
                        <input type="submit" class="btn btn-sm bg-white" name="apply" value="Apply"/>
                    </div>

                </div>
            </div>
        </div>

        <div class="card" id="song-author-filter-controls">
            <div class="card-header" id="heading">
                <h5 class="mb-0">
                    <button class="accordion-selector btn btn-secondary btn-sm" data-toggle="collapse"
                            data-target="#song-author-filter" aria-expanded="true"
                            aria-controls="song-author-filter">
                        Исполнитель
                    </button>
                </h5>
            </div>
            <!--  <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">-->
            <div id="song-author-filter" class="collapse" aria-labelledby="headingThree"
                 data-parent="#accordion-song-filter">
                <div class="card-body">
                    <label for="author-list" class="filter-label"> Выберите автора из списка</label>
                    <select class="custom-select mr-sm-2" id="author-list" name="userList" size="1">
                        <option value="username1"></option>
                        <option value="username1">Автор1</option>
                        <option value="username1">Автор2</option>
                        <option value="username1">Автор3</option>
                    </select>

                    <div class="filter-apply-controls">
                        <input type="hidden" name="action" value="findSongByName"/>
                        <input type="submit" class="btn btn-sm bg-white" name="apply" value="Apply"/>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>