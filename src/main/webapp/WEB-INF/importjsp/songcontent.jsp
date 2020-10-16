<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>

<%--12 штук на страницу--%>

<div class="item-container">
    <div class="content-values">
        <span class="item-description-song">Song-name</span>
        <span class="item-description-singer">Author</span>
        <span class="item-description-album">Album</span>
        <span class="item-description-genre">Genre</span>
        <span class="item-description-date">03-10-2020</span>
    </div>
    <div class="item-controls">
        controls
    </div>
</div>

<div class="line-separator">
    <hr/>
</div>


