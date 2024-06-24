<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <fmt:setLocale value="${sessionScope.local}" />
  <fmt:setBundle basename="localization.local"/>
  <meta charset="UTF-8">
  <title><fmt:message key="local.changerole"/></title>
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
      border-bottom: none; /* Remove border from the last item */
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

<div id="header">

  <div id="contents">
    <div class="section">
      <h1><fmt:message key="local.changerole"/></h1>
      <form action="Controller" method="post">
        <input type="hidden" name="command" value="DO_CHANGE_ROLE"/>
        <div class="form-group">
          <label for="login"><fmt:message key="local.login"/></label>
          <select class="form-control" id="login" name="login" required>
            <c:forEach var="user" items="${requestScope.userList}">
              <option value="${user.login}">${user.login}</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label for="newRole"><fmt:message key="local.newrole"/></label>
          <select class="form-control" id="newRole" name="newRole" required>
            <option value="admin"><fmt:message key="local.roleadmin"/></option>
            <option value="moderator"><fmt:message key="local.rolemoderator"/></option>
            <option value="guest"><fmt:message key="local.roleguest"/></option>
            <option value="user"><fmt:message key="local.roleuser"/></option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="local.change"/></button>
      </form>
    </div>
  </div>
</div>

</body>
</html>
