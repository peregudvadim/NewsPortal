<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          type="text/css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local"/>
    <title><fmt:message key="local.edit"/></title>
    <style>
        .form-container {
            max-width: 66.67%; /* 2/3 ширины экрана */
            margin: 0 auto; /* Центрирование контейнера */
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-container h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-container form {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/menu_new.jsp"/>

<div class="form-container">
    <h1><fmt:message key="local.edit"/></h1>

    <form action="Controller" method="post">
        <input type="hidden" name="command" value="GO_TO_EDIT_PAGE"/>
        <div class="form-group">
            <label for="title"><fmt:message key="local.newstitle"/></label>
            <select class="form-control" id="title" name="title">
                <option value="" disabled selected><fmt:message key="local.newschoose"/></option>
                <c:forEach var="newsTitle" items="${requestScope.newsTitles}">
                    <option value="${newsTitle}">${newsTitle}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="local.getnews"/></button>
    </form>

    <form action="Controller" method="post">
        <input type="hidden" name="command" value="DO_CHANGE_NEWS"/>
        <div class="form-group">
            <label for="newstitle"><fmt:message key="local.newstitle"/></label>
            <input type="text" class="form-control" id="newstitle" name="newstitle"
                   value="${requestScope.news.title}" required/>
        </div>
        <div class="form-group">
            <label for="newstext"><fmt:message key="local.newstext"/></label>
            <textarea class="form-control" rows="5" id="newstext" name="newstext" required>${requestScope.news.text}</textarea>
        </div>
        <div class="form-group">
            <label for="newsimagepath"><fmt:message key="local.newsimagepath"/></label>
            <input type="text" class="form-control" id="newsimagepath" name="newsimagepath"
                   value="${requestScope.news.imagePath}"/>
        </div>
        <div class="form-group">
            <label for="date"><fmt:message key="local.newsdate"/></label>
            <input type="text" class="form-control" id="date" name="date"
                   value="${requestScope.news.postDate}" readonly/>
        </div>
        <div class="form-group">
            <label for="userid"><fmt:message key="local.userId"/></label>
            <input type="text" class="form-control" id="userid" name="userid"
                   value="${requestScope.news.userId}" readonly/>
        </div>
        <div class="form-group">
            <label for="newsId"><fmt:message key="local.newsId"/></label>
            <input type="text" class="form-control" id="newsId" name="newsId"
                   value="${requestScope.news.id}" readonly/>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="local.changenews"/></button>
    </form>
</div>

</body>
</html>
