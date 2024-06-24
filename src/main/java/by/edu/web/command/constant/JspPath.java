package by.edu.web.command.constant;

public enum JspPath {


//    JSP_NEWS("WEB-INF/jsp/news.jsp"),
    JSP_NEWS("WEB-INF/jsp/news_new.jsp"),
    JSP_REGISTRATION("WEB-INF/jsp/registration.jsp"),
    JSP_SIGN_IN("WEB-INF/jsp/signIn.jsp"),
    JSP_ERROR("WEB-INF/jsp/error.jsp"),
    JSP_MAIN("WEB-INF/jsp/main.jsp"),
    JSP_ADMIN("WEB-INF/jsp/admin_news.jsp"),
    JSP_TEACHER("WEB-INF/jsp/teacher_news.jsp"),
    JSP_STUDENT("WEB-INF/jsp/student_news.jsp"),
    JSP_FIND("WEB-INF/jsp/find_page.jsp"),
    JSP_EDIT("WEB-INF/jsp/edit_page.jsp"),
    JSP_ADD("WEB-INF/jsp/add_page.jsp"),
    JSP_USERS_LIST("WEB-INF/jsp/list_users_page.jsp"),
    JSP_FULL_NEWS("WEB-INF/jsp/full_news.jsp"),
    JSP_CHANGE_ROLE("WEB-INF/jsp/change_role_page.jsp"),

    JSP_INDEX("index.jsp");


    private final String jspPath;


    private JspPath(String jspPath) {
        this.jspPath = jspPath;
    }

    public String getText() {
        return jspPath;
    }


}
