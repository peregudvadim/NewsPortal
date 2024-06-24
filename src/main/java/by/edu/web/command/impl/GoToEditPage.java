package by.edu.web.command.impl;

import by.edu.web.bean.News;
import by.edu.web.bean.User;
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
import java.util.List;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;

public class GoToEditPage implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(ADMIN_ROLE) || role.equals(MODERATOR_ROLE)) {
            try {
                List<String> newsTitles = newsService.getTitlesNews();
                request.setAttribute(NEWS_TITLES, newsTitles);
            } catch (ServiceException e) {
                response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_ERROR);
            }

            if (request.getParameter(TITLE_PARAM) != null) {
                String title = request.getParameter(TITLE_PARAM);
                try {
                    News news = newsService.getNewsByTitle(title);
                    request.setAttribute(NEWS, news);
                } catch (ServiceException e) {
                    response.sendRedirect(CommandRedirect.page(GO_TO_EDIT_PAGE) + MESSAGE_ERROR);
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_EDIT.getText());
            dispatcher.forward(request, response);

        } else {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LACK_OF_AUTHORITY);
        }
    }
}


