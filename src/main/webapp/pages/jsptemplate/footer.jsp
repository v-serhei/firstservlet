<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="bottom-body-container">
    <div id="footer-container">
        <footer class="f">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd">
                <div id="footer-div">
                    <span class="navbar-text" id="footer-span">footer is here</span>
                </div>
                <div class="btn-group btn-group-toggle" data-toggle="buttons" id="language-menu">
                    <label class="btn btn-secondary">
                        <button type="button" onclick="changeLanguageEN()" name="options" id="option2" autocomplete="off"> En </button>
                    </label>
                    <label class="btn btn-secondary">
                        <button type="button" onclick="changeLanguageRU()" name="options" id="option3" autocomplete="off"> Ru </button>
                    </label>
                </div>
            </nav>
        </footer>
    </div>
</div>
<div class="bottom-div-gradient">
</div>
<script type="text/javascript">
    <c:import url="/js/jquery-3.5.1.min.js"/>
    <c:import url="/js/languagechanger.js"/>
</script>
