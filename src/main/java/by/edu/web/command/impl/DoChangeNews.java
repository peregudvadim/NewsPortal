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

public class DoChangeNews implements Command {
    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter(NEWS_TITLE_PARAM);
        String text = request.getParameter(NEWS_TEXT_PARAM);
        String imagepath = request.getParameter(NEWS_IMAGE_PATH_PARAM);
        int newsId = Integer.parseInt(request.getParameter(NEWS_ID_PARAM));

        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(ADMIN_ROLE) || role.equals(MODERATOR_ROLE)) {
            try {
                newsService.changeNews(new News(title, text, imagepath, newsId));
            } catch (ServiceException e) {
                response.sendRedirect(CommandRedirect.page(GO_TO_EDIT_PAGE) + MESSAGE_ERROR);
            }
            response.sendRedirect(CommandRedirect.page(GO_TO_EDIT_PAGE) + MESSAGE_NEWS_SUCCESSFULLY_CHANGED);
        }else {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_LACK_OF_AUTHORITY);
        }

    }
}

