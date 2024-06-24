<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local"/>
    <title> <fmt:message key="local.mainbtn"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .center-screen {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 33vh;
            margin-top: 10vh;

        }
        .alert {
            font-size: 2rem;
            text-align: center;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/menu_new.jsp"/>

<div class="container center-screen">
    <div class="alert alert-success" role="alert">
        <h4 class="alert-heading"><fmt:message key="local.hellow"/> <strong>${sessionScope.user.login}!</strong></h4>
        <p><fmt:message key="local.rolehellow"/> <strong>${sessionScope.user.role}</strong></p>

        <c:if test="${sessionScope.user.role eq 'admin'}">
            <p class="mb-0"><fmt:message key="local.adminhellow"/></p>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'moderator'}">
            <p class="mb-0"><fmt:message key="local.moderhellow"/></p>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'user'}">
            <p class="mb-0"><fmt:message key="local.userhellow"/></p>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'guest'}">
            <p class="mb-0"><fmt:message key="local.guesthellow"/></p>
        </c:if>
    </div>
</div>


</body>
</html>
