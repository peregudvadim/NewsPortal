package by.edu.web.dao;

import by.edu.web.bean.AuthInfo;
import by.edu.web.bean.User;
import by.edu.web.bean.UserInfo;

import java.util.List;

public interface UserDao {

    User signIn(AuthInfo authInfo) throws DaoException;

    boolean registration(UserInfo userInfo) throws DaoException;

    int getUserIdByLogin(String login) throws DaoException;

    int getRoleId(String roleName) throws DaoException;

    String getRoleTitle(int roleId) throws DaoException;
    boolean addContacts(String phone, String email, int userId) throws DaoException;

    boolean setRole(int userId) throws DaoException;

    boolean changeRole(String login, String newRole) throws DaoException;

    String getRoleByUserId(int userId) throws DaoException;

    List<User> getUserList() throws DaoException;

    String getLoginByUserId(int userId) throws DaoException;

}
