<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"

          rel="stylesheet">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local"/>
    <title><fmt:message key="local.add"/></title>

</head>
<body>

    <c:import url="/WEB-INF/jsp/menu_new.jsp"/>



<div id="header">

    <div id="contents">
        <div class="section">
            <h1><fmt:message key="local.addnews"/></h1>
            <form action="Controller" method="post">
                <input type="hidden" name="command" value="ADD_NEWS"/>
                <div class="form-group">
                    <label for="newstitle"><fmt:message key="local.newstitle"/></label>
                    <input type="text" class="form-control" id="newstitle" name="newstitle" required>
                </div>
                <div class="form-group">
                    <label for="newstext"><fmt:message key="local.newstext"/></label>
                    <textarea class="form-control" rows="5"  id="newstext" name="newstext" required>
                    </textarea>
                </div>
                <div class="form-group">
                    <label for="newsimagepath"><fmt:message key="local.newsimagepath"/></label>
                  <input type="text" class="form-control" id="newsimagepath" name="newsimagepath">
                </div>
                <div class="form-group">
                    <label for="newscategory"><fmt:message key="local.newscategory"/></label>
                    <select
                        class="form-control" id="newscategory" name="newscategory" required>
                    <option value="sport"><fmt:message key="local.sport"/></option>
                    <option value="movie"><fmt:message key="local.movie"/></option>
                    <option value="study"><fmt:message key="local.study"/></option>




                </select>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="local.add"/></button>
            </form>

        </div>

    </div>
</div>


</body>
</html>
