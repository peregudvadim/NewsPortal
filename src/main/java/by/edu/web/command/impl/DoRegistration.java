package by.edu.web.command.impl;

import by.edu.web.bean.UserInfo;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import by.edu.web.service.UserService;
import by.edu.web.command.Command;
import by.edu.web.validation.UserValidateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;



public class DoRegistration implements Command {
    private final UserService userService = ServiceProvider.getInstance().getUserService();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        try {
            String name = request.getParameter(NAME_PARAM);
            String login = request.getParameter(LOGIN_PARAM);
            String password = request.getParameter(PASSWORD_PARAM);
            String email = request.getParameter(EMAIL_PARAM);
            String phone = request.getParameter(PHONE_PARAM);
            UserInfo userInfo = new UserInfo(name,login,password,email,phone);

            if (userService.registration(userInfo)) {
                response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE) +MESSAGE_REGISTER_SUCCESS);
            } else {
                response.sendRedirect(CommandRedirect.page(GO_TO_REGISTRATION_PAGE)+MESSAGE_REGISTER_FAILURE_CAUSE_EXIST_LOGIN);
            }

        } catch (ServiceException e) {
            response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE)+MESSAGE_ERROR);
        }
        catch (UserValidateException e){
            System.out.println(e);
            System.out.println(e.getMessage());
            response.sendRedirect(CommandRedirect.page(GO_TO_REGISTRATION_PAGE) +MESSAGE_ERROR_OF_VALIDATION);
        }
    }
}

