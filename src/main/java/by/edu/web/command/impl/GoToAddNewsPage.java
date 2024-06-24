package by.edu.web.command.impl;

import by.edu.web.command.Command;
import by.edu.web.command.constant.JspPath;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToAddNewsPage implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_ADD.getText());
        dispatcher.forward(request, response);
    }
}

