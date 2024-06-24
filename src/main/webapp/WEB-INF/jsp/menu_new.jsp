<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <style>
        .menu-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            background-color: #343a40;
            padding: 10px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }

        .menu-items {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .menu-item {
            color: white;
            text-align: center;
            padding: 10px;
            background-color: #495057;
            border-radius: 4px;
            text-decoration: none;
        }

        .menu-item:hover {
            background-color: #6c757d;
        }

        .local-container {
            display: flex;
            gap: 0;
        }

        .local-container .menu-item {
            margin: 0;
        }

        .user-info {
            color: white;
            text-align: center;
            padding: 10px;
            background-color: #495057;
            border-radius: 4px;
            margin-left: 10px;
        }

        .user-info div {
            margin-bottom: 5px;
        }

        .message-container {
            color: white;
            text-align: center;
            background-color: #495057;
            padding: 10px;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        body {
            padding-top: 100px;
        }
    </style>
</head>

<body>
<div class="menu-container">
    <div class="menu-items">
        <div class="local-container">
            <a href="Controller?command=Localization&local=ru" class="menu-item"><fmt:message key="local.ru"/></a>
            <a href="Controller?command=Localization&local=en" class="menu-item"><fmt:message key="local.en"/></a>
        </div>

        <div class="user-info">
            <div><c:out value="${sessionScope.user.login}"/></div>
            <div><c:out value="${sessionScope.user.role}"/></div>
        </div>

        <c:if test="${sessionScope.user.role eq 'admin' || sessionScope.user.role eq 'moderator' ||sessionScope.user.role eq 'user'}">
            <a href="Controller?command=GO_TO_NEWS_PAGE" class="menu-item"><fmt:message key="local.news"/></a>
        </c:if>

        <c:if test="${sessionScope.user.role eq 'admin' || sessionScope.user.role eq 'moderator'}">
            <a href="Controller?command=GO_TO_EDIT_PAGE" class="menu-item"><fmt:message key="local.edit"/></a>
        </c:if>

        <c:if test="${sessionScope.user.role eq 'admin' || sessionScope.user.role eq 'moderator'}">
            <a href="Controller?command=GO_TO_ADD_NEWS_PAGE" class="menu-item"><fmt:message key="local.add"/></a>
        </c:if>

        <a href="Controller?command=DO_SIGN_OUT" class="menu-item"><fmt:message key="local.signout"/></a>

        <c:if test="${sessionScope.user.role eq 'admin'}">
            <a href="Controller?command=GO_TO_USER_LIST" class="menu-item"><fmt:message key="local.userlist"/></a>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'admin'}">
            <a href="Controller?command=GO_TO_CHANGE_ROLE_PAGE" class="menu-item"><fmt:message key="local.changerole"/></a>
        </c:if>

    </div>
    <div class="message-container">
        <c:if test="${not (param.message eq null)}">
            <div>
                <strong><fmt:message key="local.message"/></strong> <fmt:message key="${param.message}"/>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
