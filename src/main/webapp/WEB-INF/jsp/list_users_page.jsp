<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local"/>
    <title><fmt:message key="local.userlist"/></title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" type="text/css">
    <style>
        .user-list {
            font-family: 'Arial', sans-serif;
            font-size: 16px;
            margin: 20px 0;
        }

        .user-item {
            padding: 10px;
            border-bottom: 1px solid #ccc;
        }

        .user-item:last-child {
            border-bottom: none;
        }

        .user-detail {
            margin: 5px 0;
        }

        .user-label {
            font-weight: bold;
        }

        .message {
            color: red;
            font-weight: bold;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<c:import url="/WEB-INF/jsp/menu_new.jsp"/>

<p><fmt:message key="local.userlist"/></p>

<div class="user-list">
    <c:forEach var="user" items="${requestScope.userList}">
        <div class="user-item">
            <div class="user-detail">
                <span class="user-label"><fmt:message key="local.login"/></span> <c:out value="${user.login}"/>
            </div>
            <div class="user-detail">
                <span class="user-label"><fmt:message key="local.role"/></span> <c:out value="${user.role}"/>
            </div>
            <div class="user-detail">
                <span class="user-label"><fmt:message key="local.userId"/></span> <c:out value="${user.userId}"/>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>
