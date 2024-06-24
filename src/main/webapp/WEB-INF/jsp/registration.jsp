<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
        <fmt:setLocale value="${sessionScope.local}" />
        <fmt:setBundle basename="localization.local"/>
    <meta charset="UTF-8">
    <title><fmt:message key="local.regform"/></title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" type="text/css">
    <script>
        // Регулярные выражения
        let loginRegex;
        loginRegex = /^.{4,40}$/;
        let emailRegex;
        emailRegex = /^.+@.+\..+$/;
        let passwordRegex;
        passwordRegex = /^.{4,40}$/;
        let phoneRegex;
        phoneRegex = /^\+.*$/;

        function validateForm() {
            // Получение значений полей
            const login = document.getElementById("login").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            const phone = document.getElementById("phone").value;

            // Проверка логина
            if (!loginRegex.test(login)) {
                alert("Invalid login format. Login should start with a letter and can contain letters, numbers, dashes, underscores, and dots. Length: 2-21 characters.");
                return false;
            }

            // Проверка email
            if (!emailRegex.test(email)) {
                alert("Invalid email format.");
                return false;
            }

            // Проверка пароля
            if (!passwordRegex.test(password)) {
                alert("Invalid password format. Password should be 6-20 characters long and include at least one digit, one lowercase letter, one uppercase letter, and one special character.");
                return false;
            }

            // Проверка телефона
            if (!phoneRegex.test(phone)) {
                alert("Invalid phone format. Valid formats: 123456789012, 123-456-7890, (123)456-7890, (123)4567890.");
                return false;
            }

            return true; // Если все проверки пройдены
        }
    </script>
</head>
<body>


    <c:import url="/WEB-INF/jsp/menu_for_sign.jsp"/>

    <div id="header">

        <div id="contents">
            <div class="section">
                <h1><fmt:message key="local.regform"/></h1>
                <form action="Controller" method="post" onsubmit="return validateForm()">
                        <input type="hidden" name="command" value="do_registration"/>
                    <div class="form-group">
                        <label for="name"><fmt:message key="local.name"/></label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="login"><fmt:message key="local.login"/></label>
                        <input type="text" class="form-control" id="login" name="login" required>
                    </div>
                    <div class="form-group">
                        <label for="password"><fmt:message key="local.password"/></label> <input type="password"
                                                                    class="form-control" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="local.email"/></label> <input type="email"
                                                                 class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="phone"><fmt:message key="local.phone"/></label> <input type="text"
                                                                   class="form-control" id="phone" name="phone" required>
                    </div>

                    <button type="submit" class="btn btn-primary"><fmt:message key="local.registration"/></button>
                </form>

            </div>

        </div>
    </div>
    </body>
</html>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <link rel="stylesheet" href="css/style.css" type="text/css">--%>
<%--    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"--%>

<%--          rel="stylesheet">--%>
<%--    <fmt:setLocale value="${sessionScope.local}" />--%>
<%--    <fmt:setBundle basename="localization.local"/>--%>

<%--    <title><fmt:message key="local.signup"/></title>--%>

<%--</head>--%>
<%--<body>--%>
<%--<c:import url="/WEB-INF/jsp/menu_for_sign.jsp"/>--%>

<%--<div id="header">--%>

<%--    <div id="contents">--%>
<%--        <div class="section">--%>
<%--            <h1><fmt:message key="local.regform"/></h1>--%>
<%--            <form action="Controller" method="post">--%>
<%--                    <input type="hidden" name="command" value="do_registration"/>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="name"><fmt:message key="local.name"/></label>--%>
<%--                    <input type="text" class="form-control" id="name" name="name" required>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="login"><fmt:message key="local.login"/></label>--%>
<%--                    <input type="text" class="form-control" id="login" name="login" required>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="password"><fmt:message key="local.password"/></label> <input type="password"--%>
<%--                                                                class="form-control" id="password" name="password" required>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="email"><fmt:message key="local.email"/></label> <input type="email"--%>
<%--                                                             class="form-control" id="email" name="email" required>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="phone"><fmt:message key="local.phone"/></label> <input type="text"--%>
<%--                                                               class="form-control" id="phone" name="phone" required>--%>
<%--                </div>--%>

<%--                <button type="submit" class="btn btn-primary"><fmt:message key="local.registration"/></button>--%>
<%--            </form>--%>

<%--        </div>--%>

<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
