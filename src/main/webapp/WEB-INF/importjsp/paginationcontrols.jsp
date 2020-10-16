<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>

<%--12 штук на страницу--%>

<%--<div class="btn-toolbar btn-group-sm justify-content-center song-pagination-block">
    <span class="pagination-info">Cтраница 1 из 5:&nbsp&nbsp&nbsp </span>
    <div class="pagination-controls">

        <div class="btn-group  btn-group-sm" role="group" aria-label="Third group">
            <a class="pagination-link" href="#"> << </a>
        </div>
        <div class="btn-group mr-2" role="group" aria-label="First group">
            <a class="pagination-link" href="#"> < </a>
        </div>

        <div class="btn-group mr-2 btn-group-sm" role="group" aria-label="Second group">
            <a class="pagination-link" href="#"> 1 </a>
            <span class="pagination-link active-page-link"> 22</span>
            <a class="pagination-link" href="#"> 3 </a>
        </div>

        <div class="btn-group  btn-group-sm" role="group" aria-label="Third group">
            <a class="pagination-link" href="#"> > </a>
        </div>

        <div class="btn-group mr-2" role="group" aria-label="First group">
            <a class="pagination-link" href="#"> >> </a>
        </div>

    </div>
</div>--%>

<div class="btn-toolbar btn-group-sm justify-content-center song-pagination-block">
    <span class="pagination-info">Cтраница 4 из 6: </span>
    <div class="pagination-controls">
        <div class="btn-group  btn-group-sm" role="group" aria-label="Third group">
            <a class="pagination-link" href="#?action=pagination"> << </a>
        </div>
        <div class="btn-group mr-2" role="group" aria-label="First group">
            <a class="pagination-link" href="#?action=pagination"> < </a>
        </div>
        <div class="btn-group mr-2 btn-group-sm" role="group" aria-label="Second group">
            <a class="pagination-link" href="#?action=pagination">3</a>
            <span class="pagination-link active-page-link">4</span>
            <a class="pagination-link" href="#?action=pagination">5</a>
        </div>
        <div class="btn-group  btn-group-sm" role="group" aria-label="Third group">
            <a class="pagination-link" href="#?action=pagination"> > </a>
        </div>
        <div class="btn-group mr-2" role="group" aria-label="First group">
            <a class="pagination-link" href="#?action=pagination"> >> </a>
        </div>
    </div>

</div>
