package by.edu.web.service.impl;

import by.edu.web.bean.AuthInfo;
import by.edu.web.bean.User;
import by.edu.web.bean.UserInfo;
import by.edu.web.dao.DaoException;
import by.edu.web.dao.DaoProvider;
import by.edu.web.dao.UserDao;
import by.edu.web.service.ServiceException;
import by.edu.web.service.UserService;
import by.edu.web.validation.UserValidateException;
import by.edu.web.validation.UserValidation;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public boolean registration(UserInfo regInfo) throws ServiceException, UserValidateException {
        UserValidation.Builder valid = new UserValidation.Builder();
        UserValidation validResult = valid.validLogin(regInfo.getLogin()).validPassword(regInfo.getPassword())
                .validEmail(regInfo.getEmail()).validPhone(regInfo.getPhone()).build();
        if (validResult.isResult()) {
            throw new UserValidateException(validResult.getException());
        } else {
            try {
                return userDao.registration(regInfo);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public User signIn(AuthInfo authInfo) throws ServiceException, UserValidateException {
        UserValidation.Builder valid = new UserValidation.Builder();
        UserValidation validResult = valid.validLogin(authInfo.getLogin()).validPassword(authInfo.getPassword()).build();
        if (validResult.isResult()) {
            throw new UserValidateException(validResult.getException());
        } else {
            try {
                return userDao.signIn(authInfo);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }



    @Override
    public int getUserId(String login) throws ServiceException {

        int userId = 0;
        try {
            userId = userDao.getUserIdByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return userId;
    }


    @Override
    public boolean changeRole(String login,String newRole) throws ServiceException {

        try {
            return userDao.changeRole(login,newRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getUserList() throws ServiceException {
        try {
            return userDao.getUserList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


}


