package by.edu.web.command.impl;

import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import by.edu.web.command.constant.CommandName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

import static by.edu.web.command.constant.ParamConst.*;

public class Localization implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String localeParam = request.getParameter(ATTR_LOCAL);
        if (localeParam != null) {
            request.getSession().setAttribute(ATTR_LOCAL, localeParam);
        }


        String referer = request.getHeader(REFERER);


        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(CommandRedirect.page(CommandName.GO_TO_NEWS_PAGE));
        }




    }
}
