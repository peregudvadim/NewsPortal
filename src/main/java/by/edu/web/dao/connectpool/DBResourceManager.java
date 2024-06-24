package by.edu.web.dao.connectpool;

import java.util.ResourceBundle;

public class DBResourceManager {

    private final static DBResourceManager instance = new DBResourceManager();


    private final ResourceBundle jdbcProperties = ResourceBundle.getBundle("db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return jdbcProperties.getString(key);
    }

}