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
    private final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_USER_LIST = "SELECT * FROM users WHERE login <> ?";
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, name) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_CONTACTS = "INSERT INTO contacts (phone, email, user_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_LOGIN_PASS = "SELECT * FROM users WHERE login = ? AND password = ?";
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_SELECT_ROLE_ID = "SELECT * FROM roles WHERE title = ?";
    private static final String SQL_SELECT_ROLE_TITLE = "SELECT * FROM roles WHERE role_id = ?";
    private static final String SQL_INSERT_ROLE = "INSERT INTO roles_has_users (roles_role_id, users_user_id) VALUES (?, ?)";
    private static final String SQL_CHANGE_ROLE = "UPDATE roles_has_users SET roles_role_id = ? WHERE users_user_id = ?";
    private static final String SQL_SELECT_ROLE_HAS_USER = "SELECT * FROM roles_has_users WHERE users_user_id = ?";
    private static final String SQL_SELECT_LOGIN_BY_USER_ID = "SELECT * FROM users WHERE user_id = ?";




    @Override
    public boolean registration(UserInfo userInfo) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;

        try {
            if(getUserIdByLogin(userInfo.getLogin())>0){
                return false;
            }

            con = pool.takeConection();
            ps = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userInfo.getLogin());
            ps.setString(2, userInfo.getPassword());
            ps.setString(3, userInfo.getName());

            ps.executeUpdate();
            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newUserId = (int) generatedKeys.getLong(1);
                addContacts(userInfo.getPhone(), userInfo.getEmail(), newUserId);
                setRole(newUserId);
            }

            generatedKeys.close();
            return true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to register new user!", e);

        } finally {

            pool.closeConnection(ps, con);
        }

    }

    @Override
    public int getRoleId(String roleTitle) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        int roleId = 0;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_ROLE_ID);
            ps.setString(1, roleTitle);
            rs = ps.executeQuery();
            if (rs.next()) {
                roleId = rs.getInt("role_id");
            }
            return roleId;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get roleId", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }

    }

    @Override
    public String getRoleTitle(int roleId) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String roleTitle = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_ROLE_TITLE);
            ps.setInt(1, roleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                roleTitle = rs.getString("title");
            }
            return roleTitle;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get roleTitle", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }

    }

    public String getRoleByUserId(int userId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_ROLE_HAS_USER);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            int roleID = 2;
            if (rs.next()) {
                roleID = rs.getInt("roles_role_id");
            }
            return getRoleTitle(roleID);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to add new role!", e);
        } finally {
            pool.closeConnection(ps, con, rs);

        }
    }


    @Override
    public List<User> getUserList() throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<User> userList = new ArrayList<>();
        int userId = 0;
        String login = null;
        String role = null;
        String excludedLogin = "admin";
        try {
            con = pool.takeConection();

            ps = con.prepareStatement(SQL_SELECT_USER_LIST);
            ps.setString(1,excludedLogin);
            rs = ps.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("user_id");
                login = rs.getString("login");
                role = getRoleByUserId(userId);

                userList.add(new User(login, role, userId));
            }
            return userList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get User list", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }

    @Override
    public String getLoginByUserId(int userId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String login=null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_LOGIN_BY_USER_ID);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                login = rs.getString("login");

            }
            return login;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to get login", e);
        } finally {
            pool.closeConnection(ps, con, rs);

        }
    }


    @Override
    public boolean addContacts(String phone, String email, int userId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_INSERT_CONTACTS);
            ps.setString(1, phone);
            ps.setString(2, email);
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to add new contacts!", e);
        } finally {
            pool.closeConnection(ps, con);
        }
    }

    @Override
    public boolean setRole(int userId) throws DaoException {

        Connection con = null;
        PreparedStatement ps = null;
        int roleIdByDefault = 2; //GUEST
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_INSERT_ROLE);
            ps.setInt(1, 2);
            ps.setInt(roleIdByDefault, userId);

            return ps.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to add new role!", e);
        } finally {
            pool.closeConnection(ps, con);
        }
    }

    @Override
    public boolean changeRole(String login,String newRole) throws DaoException {

        Connection con = null;
        PreparedStatement ps = null;
        int userId = getUserIdByLogin(login);
        int newRoleId = getRoleId(newRole);
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_CHANGE_ROLE);
            ps.setInt(1, newRoleId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to change role!", e);
        } finally {
            pool.closeConnection(ps, con);
        }
    }


    @Override
    public User signIn(AuthInfo authInfo) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_LOGIN_PASS);
            ps.setString(1, authInfo.getLogin());
            ps.setString(2, authInfo.getPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                String login = rs.getString("login");
                int userId = rs.getInt("user_id");
                String roleTitle = getRoleByUserId(userId);
                return new User(login, roleTitle, userId);
            }
            return null;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to signIn!", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }


    @Override
    public int getUserIdByLogin(String login) throws DaoException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = pool.takeConection();
            ps = con.prepareStatement(SQL_SELECT_LOGIN);
            ps.setString(1, login);

            rs = ps.executeQuery();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
            return userId;

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Error to return UserId!", e);

        } finally {
            pool.closeConnection(ps, con, rs);
        }
    }

}
