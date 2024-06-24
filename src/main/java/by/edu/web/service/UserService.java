package by.edu.web.service;


import by.edu.web.bean.AuthInfo;
import by.edu.web.bean.User;
import by.edu.web.bean.UserInfo;
import by.edu.web.dao.DaoException;
import by.edu.web.validation.UserValidateException;

import java.util.List;

public interface UserService {

    User signIn(AuthInfo authInfo) throws ServiceException, UserValidateException;
    boolean registration(UserInfo userInfo) throws ServiceException, UserValidateException;
    int getUserId(String login) throws ServiceException;

    boolean changeRole(String login,String newRole) throws ServiceException;

    List<User> getUserList() throws ServiceException;

}
