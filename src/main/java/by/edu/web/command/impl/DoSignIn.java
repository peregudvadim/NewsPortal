package by.edu.web.command.impl;

import by.edu.web.bean.AuthInfo;
import by.edu.web.bean.User;
import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import by.edu.web.service.UserService;
import by.edu.web.validation.UserValidateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;


public class DoSignIn implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user;
        try {
            String login = request.getParameter(LOGIN_PARAM);
            String password = request.getParameter(PASSWORD_PARAM);
             user = userService.signIn(new AuthInfo(login,password));

                if (user != null) {
                HttpSession session = (HttpSession) request.getSession(true);
                session.setAttribute(ATTR_USER, user);


                response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LOGGED);

            } else {
                response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE) + MESSAGE_USER_NOT_FOUND);
            }

        } catch (ServiceException e) {
            response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE) + MESSAGE_ERROR);
        }
        catch (UserValidateException e){
            response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE) +MESSAGE_ERROR_OF_VALIDATION);
        }

    }
}

