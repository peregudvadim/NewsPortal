package by.edu.web.command.impl;

import by.edu.web.bean.News;
import by.edu.web.bean.User;
import by.edu.web.command.Command;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.NewsService;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.edu.web.command.constant.CommandName.*;
import static by.edu.web.command.constant.ParamConst.*;


public class AddNews implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String title = request.getParameter(NEWS_TITLE_PARAM);
        String text = request.getParameter(NEWS_TEXT_PARAM);
        String imagePath = request.getParameter(NEWS_IMAGE_PATH_PARAM);
        String category = request.getParameter(NEWS_CATEGORY_PARAM);
        int userId = ((User) request.getSession().getAttribute(ATTR_USER)).getUserId();

        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(ADMIN_ROLE) || role.equals(MODERATOR_ROLE)) {

            try {
                newsService.addNews(new News(title, text, imagePath, userId, category));
            } catch (ServiceException e) {
                response.sendRedirect(CommandRedirect.page(GO_TO_NEWS_PAGE) + MESSAGE_ERROR);
            }

            response.sendRedirect(CommandRedirect.page(GO_TO_NEWS_PAGE) + MESSAGE_NEWS_SUCCESSFULLY_ADDED);
        } else {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LACK_OF_AUTHORITY);
        }
    }
}