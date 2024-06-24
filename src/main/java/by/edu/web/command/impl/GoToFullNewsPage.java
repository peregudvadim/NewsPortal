package by.edu.web.command.impl;

import by.edu.web.bean.News;
import by.edu.web.command.Command;
import by.edu.web.command.constant.JspPath;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.NewsService;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

public class GoToFullNewsPage implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int newsId=Integer.parseInt(request.getParameter(NEWS_ID_PARAM));
            News news = newsService.getNewsByID(newsId);

            request.setAttribute(NEWS, news);
            RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_FULL_NEWS.getText());
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(CommandRedirect.page(GO_TO_NEWS_PAGE) + MESSAGE_GET_NEWS_LIST_FAILURE);

        }
    }
}

