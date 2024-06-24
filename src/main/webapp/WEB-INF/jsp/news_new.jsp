<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local"/>
    <title> <fmt:message key="local.news"/></title>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .news-block {
            margin-bottom: 20px;
        }

        .news-text {
            height: 50px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .fixed-pagination {
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #f8f9fa;
            padding: 3px 0;
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.1);
        }

        .fixed-pagination .pagination-lg .page-link {
            padding: 3px 6px;
            font-size: 1rem;
        }

        .fixed-pagination .pagination-lg .page-item {
            margin: 0 2px;
        }

        .category-form {
            margin-top: 15px;
        }

        .content-container {
            margin-bottom: 80px;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/menu_new.jsp"/>

<!-- Category selection form -->
<div class="container category-form">
    <div class="row">
        <div class="col-md-12">
            <form action="Controller" method="post" class="form-inline">
                <input type="hidden" name="command" value="GO_TO_NEWS_PAGE"/>
                <div class="form-group mb-2">
                    <label for="category" class="mr-2"><fmt:message key="local.newscategory"/></label>
                    <select class="form-control mr-2" id="category" name="category">
                        <option value="" disabled selected><fmt:message key="local.allcategories"/></option>
                        <c:forEach var="category" items="${requestScope.categoryList}">
                            <option value="${category}">${category}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary mb-2"><fmt:message key="local.accept"/></button>
            </form>
        </div>
    </div>
</div>


<div class="container content-container">
    <div class="row">
        <c:forEach var="news" items="${requestScope.latestList}" varStatus="status">
            <div class="col-md-4 news-block">
                <div class="card">
                    <img src="${news.imagePath}" class="card-img-top" alt="News Image">
                    <div class="card-body">
                        <h5 class="card-title">${news.title}</h5>
                        <p class="card-text news-text">
                            <c:choose>
                                <c:when test="${fn:length(news.text) > 50}">
                                    ${fn:substring(news.text, 0, 50)}...
                                    <a href="Controller?command=GO_TO_FULL_NEWS_PAGE&newsId=${news.id}"><fmt:message key="local.viewmore"/> </a>
                                </c:when>
                                <c:otherwise>
                                    ${news.text}
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p class="card-text"><small class="text-muted"><fmt:message key="local.publicdate"/> ${news.postDate}</small></p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<div class="fixed-pagination">
    <ul class="pagination pagination-lg justify-content-center">
        <c:forEach var="i" begin="1" end="${noOfPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <li class="page-item active">
                        <a class="page-link" href="Controller?command=GO_TO_NEWS_PAGE&page=${i}&category=${requestScope.category}">${i}
                            <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="Controller?command=GO_TO_NEWS_PAGE&page=${i}&category=${requestScope.category}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>

</body>
</html>
