package by.edu.web.bean;

import java.io.Serial;
import java.io.Serializable;
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int userId;
    private String login;
    private String role;



    public User() {
    }

    public User(String login, String role, int userId) {
        this.login = login;
        this.role = role;
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!login.equals(user.login)) return false;
        return role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + login.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}