package by.edu.web.dao;

import by.edu.web.dao.impl.SQLNewsDao;
import by.edu.web.dao.impl.SQLUserDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();

    private final UserDao userDao = new SQLUserDao();
    private final NewsDao newsDao = new SQLNewsDao();

    private DaoProvider() {}

    public UserDao getUserDao() {
        return userDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }
    public static DaoProvider getInstance() {
        return instance;
    }

}