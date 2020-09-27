<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language/jsp"/>
<div id="bottom-body-container">
    <div id="footer-container">
        <footer class="f">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
                <div id="footer-div">
                    <span class="navbar-text" id="footer-span"> <fmt:message key="footer.message"/></span>
                </div>
                <div id="lang-menu" style="display: flex">
                    <form action="">
                        <div class="btn-group btn-group-sm" role="group" aria-label="Button group with nested dropdown">
                            <button type="button" class="btn btn-secondary">En</button>
                        </div>
                    </form>
                    <form action="">
                        <div class="btn-group btn-group-sm" role="group" aria-label="Button group with nested dropdown">
                            <button type="button" class="btn btn-secondary">Ru</button>
                        </div>
                    </form>
                </div>
            </nav>
        </footer>
    </div>
</div>
<div class="bottom-div-gradient">
</div>
<!-- Add jquery libs on page -->
<script type="text/javascript">
    <c:import url="/js/jquery-3.5.1.min.js"/>
    <c:import url="/css/bootstrap/js/bootstrap.min.js"/>
</script>
