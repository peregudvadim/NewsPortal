package by.edu.web.dao.impl;

import by.edu.web.bean.AuthInfo;
import by.edu.web.bean.User;
import by.edu.web.bean.UserInfo;
import by.edu.web.dao.UserDao;
import by.edu.web.dao.DaoException;
import by.edu.web.dao.connectpool.ConnectionPool;
import by.edu.web.dao.connectpool.ConnectionPoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDao implements UserDao {

    private static final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String EXCLUDED_LOGIN = "admin";

    private static final String SQL_SELECT_USER_LIST =
            "SELECT * FROM users WHERE login <> ?";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (login, password, name) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_CONTACTS =
            "INSERT INTO contacts (phone, email, user_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_LOGIN_PASS =
            "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String SQL_SELECT_LOGIN =
            "SELECT * FROM users WHERE login = ?";
    private static final String SQL_SELECT_ROLE_ID =
            "SELECT * FROM roles WHERE title = ?";
    private static final String SQL_SELECT_ROLE_TITLE =
            "SELECT * FROM roles WHERE role_id = ?";
    private static final String SQL_INSERT_ROLE =
            "INSERT INTO roles_has_users (roles_role_id, users_user_id) VALUES (?, ?)";
    private static final String SQL_CHANGE_ROLE =
            "UPDATE roles_has_users SET roles_role_id = ? WHERE users_user_id = ?";
    private static final String SQL_SELECT_ROLE_HAS_USER =
            "SELECT * FROM roles_has_users WHERE users_user_id = ?";
    private static final String SQL_SELECT_LOGIN_BY_USER_ID =
            "SELECT * FROM users WHERE user_id = ?";

    private static final int DEFAULT_ROLE_ID = 2; // GUEST

    @Override
    public boolean registration(UserInfo userInfo) throws DaoException {
        if (getUserIdByLogin(userInfo.getLogin()) > 0) return false;

        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, userInfo.getLogin());
            ps.setString(2, userInfo.getPassword());
            ps.setString(3, userInfo.getName());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    addContacts(userInfo.getPhone(), userInfo.getEmail(), userId);
                    setRole(userId);
                }
            }
            return true;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error registering new user", e);
        }
    }

    @Override
    public int getRoleId(String roleTitle) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_ROLE_ID)
        ) {
            ps.setString(1, roleTitle);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("role_id") : 0;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting role ID", e);
        }
    }

    @Override
    public String getRoleTitle(int roleId) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_ROLE_TITLE)
        ) {
            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("title") : null;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting role title", e);
        }
    }

    public String getRoleByUserId(int userId) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_ROLE_HAS_USER)
        ) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                int roleId = rs.next() ? rs.getInt("roles_role_id") : DEFAULT_ROLE_ID;
                return getRoleTitle(roleId);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting user role", e);
        }
    }

    @Override
    public List<User> getUserList() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_USER_LIST)
        ) {
            ps.setString(1, EXCLUDED_LOGIN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String login = rs.getString("login");
                    String role = getRoleByUserId(userId);
                    userList.add(new User(login, role, userId));
                }
            }
            return userList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting user list", e);
        }
    }

    @Override
    public String getLoginByUserId(int userId) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_LOGIN_BY_USER_ID)
        ) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("login") : null;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting login by userId", e);
        }
    }

    @Override
    public boolean addContacts(String phone, String email, int userId) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_CONTACTS)
        ) {
            ps.setString(1, phone);
            ps.setString(2, email);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error adding contacts", e);
        }
    }

    @Override
    public boolean setRole(int userId) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_ROLE)
        ) {
            ps.setInt(1, DEFAULT_ROLE_ID);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error setting default role", e);
        }
    }

    @Override
    public boolean changeRole(String login, String newRole) throws DaoException {
        int userId = getUserIdByLogin(login);
        int newRoleId = getRoleId(newRole);

        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_CHANGE_ROLE)
        ) {
            ps.setInt(1, newRoleId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error changing user role", e);
        }
    }

    @Override
    public User signIn(AuthInfo authInfo) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_LOGIN_PASS)
        ) {
            ps.setString(1, authInfo.getLogin());
            ps.setString(2, authInfo.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String login = rs.getString("login");
                    String role = getRoleByUserId(userId);
                    return new User(login, role, userId);
                }
                return null;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error during sign-in", e);
        }
    }

    @Override
    public int getUserIdByLogin(String login) throws DaoException {
        try (
                Connection con = pool.takeConection();
                PreparedStatement ps = con.prepareStatement(SQL_SELECT_LOGIN)
        ) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("user_id") : 0;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error getting user ID by login", e);
        }
    }
}
