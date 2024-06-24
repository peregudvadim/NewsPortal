package by.edu.web.command.impl;

import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

public class NoSuchCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(CommandRedirect.page(GO_TO_SIGN_IN_PAGE) + MESSAGE_ERROR);
    }
}
