<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<%@ taglib prefix="ab" uri="customTagLib" %>
<div class="compilation-content-block">
    <div class="compilation-description-block">
        <div class="compilation-create-header">
            <h5><fmt:message key="compilation.create.header"/></h5>
            <div class="compilation-result">
                <span class="compilation-count-result"><fmt:message
                        key="compilation.create.song.count.caption"/> &nbsp; ${requestScope.compilationContentList.size()}</span>
            </div>
        </div>
        <div class="compilation-items">
            <c:forEach var="song" items="${requestScope.compilationContentList}">
                <div class="compilation-content-description">
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
                        <div class="item-controls">
                            <form class="form-group" action="${pageContext.request.contextPath}/do/admin/compilation/basket_remove"
                                  method="get">
                                <input type="hidden" name="currentPageUri" value="${sessionScope.lastUri}"/>
                                <input type="hidden" name="orderedSongId" value="${song.id}"/>
                                <input type="submit" class="btn btn-sm song-content-controls bg-warning"
                                       style="background-image: url('${pageContext.request.contextPath}/resources/img/remove.png')"
                                       alt="Remove" value=""
                                       data-toggle="tooltip" data-placement="top"
                                       title="<fmt:message key="operation.compilation.remove.song.controls.hint"/>"/>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="line-separator">
                    <hr/>
                </div>
            </c:forEach>
        </div>
    </div>
    <c:if test="${requestScope.enableCompilationControls}">
        <div class="compilation-params-block">
            <form class="form-group" action="${pageContext.request.contextPath}/do/admin/compilation/create_compilation" method="post">
                <label for="compilation-title"> <fmt:message key="compilation.create.title.field.caption"/> </label>
                <input class="form-control" id="compilation-title"
                       type="text"
                       name="compilationName"
                       pattern="[а-яА-Яa-zA-Z\d'\\!\\.\\-\\,\\+\s]{1,80}"
                       required
                       minlength="1"
                       maxlength="80"/>
                <label for="compilation-type"><fmt:message key="compilation.create.type.field.caption"/></label>
                <select class="custom-select mr-sm-2" name="compilationTypeName" id="compilation-type" required>
                    <option selected value=""></option>
                    <c:forEach var="type" items="${requestScope.compilationTypeList}">
                        <option value="${type}">${type}</option>
                    </c:forEach>
                </select>
                <div class="compilation-controls">
                    <input type="submit" class="btn btn-sm btn-secondary bg-success"
                           name="action" value="<fmt:message key="compilation.create.btn"/>" style="margin: 0 auto"/>
                </div>
            </form>
        </div>
    </c:if>
    <c:if test="${requestScope.enableMessage}">
        <div class="admin-operation-message-block">
            <span class="admin-message"><fmt:message key="${requestScope.resultMessage}"/></span>
        </div>
    </c:if>
</div>