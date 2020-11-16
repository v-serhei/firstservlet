<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="right-menu-div">
            <span class="right-menu-label">
                <fmt:message key="compilation.search.header"/>
            </span>
    <div class="song-searching-controls">
        <form action="${pageContext.request.contextPath}/do/compilations/search" method="get">
            <c:set var="filter" value="${sessionScope.compilationFilter}"/>
            <input type="hidden" name="contentType" value="${filter.contentType}"/>
            <input type="hidden" name="filtered" value="true"/>
            <label for="song-name-input" class="search-label search-block-item">
                <fmt:message key="compilation.search.input.label"/>
            </label>
            <input class="search-text-input" id="song-name-input" type="text" name="compilationName" value="${filter.compilationTitle}"/>
            <div class="search-confirm-input">
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                       name="search" value="<fmt:message key="search.confirm.btn"/>"/>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
            <span class="right-menu-label">
                <fmt:message key="compilation.search.result"/> ${sessionScope.compilationContentCount}
            </span>
            <div class="line-separator">
                <hr/>
            </div>
            <span class="right-menu-label search-block-item">
                    <fmt:message key="compilation.filter.header"/>
                </span>
            <label for="compilation-type-list" class="search-label search-block-item">
                <fmt:message key="compilation.filter.type.label"/></label>
            <select class="custom-select mr-sm-2" id="compilation-type-list" name="compilationTypeName" size="1">
                <option value="" selected></option>
                <c:forEach var="type" items="${sessionScope.compilationTypeList}">
                    <c:if test="${type eq filter.compilationType}">
                        <option selected value="${type}">${type}</option>
                    </c:if>
                    <option value="${type}">${type}</option>
                </c:forEach>
            </select>
            <div class="search-confirm-input">
                <input type="submit" class="btn btn-sm bg-light search-confirm-btn"
                       name="search" value="<fmt:message key="search.confirm.btn"/>"/>
            </div>
            <div class="line-separator">
                <hr/>
            </div>
        </form>
        <form method="get">
            <input type="hidden" name="filtered" value="false"/>
            <div class="cancel-filter">
                <a class="btn btn-sm bg-light"
                   href="${pageContext.request.contextPath}/do/compilations"><fmt:message
                        key="filter.reset.btn"/> </a>
            </div>
        </form>
    </div>
</div>