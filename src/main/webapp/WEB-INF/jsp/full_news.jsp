<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="local.fullnews"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .news-container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
        }
        .back-btn {
            margin-top: 20px;
        }
        .news-image {
            max-width: 640px;
            max-height: 480px;
            width: auto;
            height: auto;
            margin-bottom: 10px;
        }
        .news-title {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .news-text {
            font-size: 18px;
            line-height: 1.6;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/menu_new.jsp"/>
<div class="container news-container">
    <h1 class="news-title">${requestScope.news.title}</h1>
    <img src="${requestScope.news.imagePath}" class="img-fluid rounded news-image" alt="News Image">
    <p class="mt-3 news-text">${requestScope.news.text}</p>
    <p class="text-muted"><fmt:message key="local.publicdate"/> ${requestScope.news.postDate}</p>
    <a href="Controller?command=GO_TO_NEWS_PAGE" class="btn btn-primary back-btn"><fmt:message key="local.back"/></a>
</div>

</body>
</html>
