package by.edu.web.command.impl;

import by.edu.web.command.constant.JspPath;
import by.edu.web.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToMainPage implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_MAIN.getText());
        dispatcher.forward(request, response);
    }
}
