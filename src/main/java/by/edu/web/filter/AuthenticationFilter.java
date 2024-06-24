package by.edu.web.filter;

import java.io.IOException;

import by.edu.web.command.CommandRedirect;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final String[] loginRequiredURLs = {
            CommandRedirect.page(GO_TO_FULL_NEWS_PAGE),
            CommandRedirect.page(GO_TO_NEWS_PAGE),
            CommandRedirect.page(GO_TO_MAIN_PAGE),
            CommandRedirect.page(GO_TO_NEWS_PAGE),
            CommandRedirect.page(GO_TO_EDIT_PAGE),
            CommandRedirect.page(GO_TO_ADD_NEWS_PAGE),
            CommandRedirect.page(GO_TO_FULL_NEWS_PAGE),
            CommandRedirect.page(DO_CHANGE_ROLE),
            CommandRedirect.page(DO_CHANGE_NEWS),
            CommandRedirect.page(GO_TO_CHANGE_ROLE_PAGE),
            CommandRedirect.page(ADD_NEWS),
            CommandRedirect.page(GO_TO_USER_LIST),
            CommandRedirect.page(DO_SIGN_OUT),
    };



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        // Проверяем, требуется ли аутентификация для текущего запроса
        boolean isLoginRequired = isLoginRequired(httpRequest);

        if (!isLoginRequired || session != null && session.getAttribute(ATTR_USER) != null) {
            // Если аутентификация не требуется или пользователь аутентифицирован, передаем управление дальше
            chain.doFilter(request, response);
        } else {

            // Если требуется аутентификация и пользователь не аутентифицирован, перенаправляем на страницу входа
            request.getRequestDispatcher(CommandRedirect.page(GO_TO_SIGN_IN_PAGE)).forward(request, response);
        }
    }

    private boolean isLoginRequired(HttpServletRequest httpRequest) {
        String requestURL = (httpRequest.getRequestURL().toString() + "?" + httpRequest.getQueryString()).toUpperCase();

        // Проверяем, есть ли текущий URI в массиве URL-адресов, требующих аутентификации
        for (String loginRequiredURL : loginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL.toUpperCase())) {

                return true;
            }

        }
        return false;
    }


    @Override
    public void destroy() {

    }
}
