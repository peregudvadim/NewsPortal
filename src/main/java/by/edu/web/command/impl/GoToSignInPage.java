package by.edu.web.command.impl;

import by.edu.web.command.Command;
import by.edu.web.command.constant.JspPath;
import by.edu.web.command.CommandRedirect;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.GO_TO_MAIN_PAGE;
import static by.edu.web.command.constant.ParamConst.*;

public class GoToSignInPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(ATTR_USER) != null) {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LOG_OUT_BEFORE);
        }

        request.getRequestDispatcher(JspPath.JSP_SIGN_IN.getText()).forward(request, response);

    }
}
