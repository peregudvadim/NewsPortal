package by.edu.web.command.impl;

import by.edu.web.bean.User;
import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import by.edu.web.command.constant.JspPath;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import by.edu.web.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

import java.io.IOException;
import java.util.List;

public class GoToChangeRolePage implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(ADMIN_ROLE)) {
            try {
                List<User> userList = userService.getUserList();
                request.setAttribute(USER_LIST, userList);
                RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_CHANGE_ROLE.getText());
                dispatcher.forward(request, response);
            } catch (ServiceException e) {
                response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_ERROR);
            }
        } else {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LACK_OF_AUTHORITY);
        }
    }
}
