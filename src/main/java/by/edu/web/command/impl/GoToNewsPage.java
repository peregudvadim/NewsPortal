package by.edu.web.command.impl;

import by.edu.web.bean.News;
import by.edu.web.bean.User;
import by.edu.web.command.CommandRedirect;
import by.edu.web.service.NewsService;
import by.edu.web.service.ServiceException;
import by.edu.web.service.ServiceProvider;
import by.edu.web.command.Command;
import by.edu.web.command.constant.JspPath;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.edu.web.command.constant.CommandName.GO_TO_MAIN_PAGE;
import static by.edu.web.command.constant.ParamConst.*;

public class GoToNewsPage implements Command {

    private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int limit = 6;
        User user = (User) request.getSession().getAttribute(ATTR_USER);
        String role = user.getRole();
        if (role.equals(GUEST_ROLE)){

            RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_MAIN.getText());
            dispatcher.forward(request, response);
        }

        if (request.getParameter(PAGE_PARAM) != null)
            try {
                page = Integer.parseInt(request.getParameter(PAGE_PARAM));
            } catch (NumberFormatException e) {
                page = 1; // default
            }

        try {
            String category = request.getParameter(CATEGORY_PARAM);
            if(category==null){
                category=ALL_CATEGORIES;
            }
            List<String> categoryList = newsService.getCategoryList();
            request.setAttribute(CATEGORY_LIST,categoryList);
            List<News> newsList = newsService.getNewsList((page - 1) * limit, limit, category);
            int noOfRecords = newsService.getNoOfRecords(category);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / limit);


            request.setAttribute(ATTR_LATEST_LIST, newsList);
            request.setAttribute(ATTR_NO_OF_PAGES, noOfPages);
            request.setAttribute(ATTR_CURRENT_PAGE, page);
            request.setAttribute(CATEGORY_PARAM, category);


            RequestDispatcher dispatcher = request.getRequestDispatcher(JspPath.JSP_NEWS.getText());
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(CommandRedirect.page(GO_TO_MAIN_PAGE) + MESSAGE_ERROR);

        }
    }
}



