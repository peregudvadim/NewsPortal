package by.edu.web.command.impl;

import by.edu.web.bean.User;
import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import by.edu.web.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

import java.io.IOException;


public class DoChangeRole implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String login = request.getParameter(LOGIN_PARAM);
        String newRole = request.getParameter(NEW_ROLE_PARAM);
        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(ADMIN_ROLE)) {
            try {
                if (userService.changeRole(login, newRole)) {
                    response.sendRedirect(CommandRedirect.page(GO_TO_CHANGE_ROLE_PAGE) + MESSAGE_CHANGE_ROLE_SUCCESSFULL);
                } else {
                    response.sendRedirect(CommandRedirect.page(GO_TO_CHANGE_ROLE_PAGE) + MESSAGE_CHANGE_ROLE_FAILURE);
                }
            } catch (ServiceException e) {
                response.sendRedirect(CommandRedirect.page(GO_TO_CHANGE_ROLE_PAGE) + MESSAGE_ERROR);
            }
        }else {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LACK_OF_AUTHORITY);
        }
    }
}
