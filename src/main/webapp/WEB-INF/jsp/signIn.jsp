<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="css/boot.css" type="text/css">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local"/>
    <title> <fmt:message key="local.signin"/></title>

</head>
<body>
<c:import url="/WEB-INF/jsp/menu_for_sign.jsp"/>
<div id="contents">

    <div class="section">

        <h1> <fmt:message key="local.signin"/></h1>
        <p>
            <fmt:message key="local.regmess"/>
        </p>
        <p style="opacity:50%"><fmt:message key="local.regmess2"/></p>

        <form action="Controller" method="post">
            <input type="hidden" name="command" value="do_sign_in"/>
            <div class="form-group">
                <label for="login"><fmt:message key="local.login"/></label>
                <input type="text" class="form-control" id="login" name="login" required>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="local.password"/></label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>

            <button type="submit" class="btn btn-primary"><fmt:message key="local.authorization"/></button>
        </form>

    </div>

    <div>

        <a href="Controller?command=go_to_registration_page"><img src="images/dobro.jpg" alt="img"
                                                                  style="width:250px; margin-left:100px; margin-top:50px;"></a>
         </div>


</div>
<div>

</div>
</body>
</html>
