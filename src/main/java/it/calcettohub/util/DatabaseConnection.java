package it.calcettohub.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    Properties p = new Properties();
    private Connection connection;
    String url;
    String user;
    String password;

    private DatabaseConnection() {
        try (InputStream dbProp = getClass().getClassLoader().getResourceAsStream("db.properties")){
            p.load(dbProp);
            this.url = p.getProperty("db_url");
            this.user = p.getProperty("db_user");
            this.password = p.getProperty("db_password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException | ClassNotFoundException _) {
            System.err.println("Connection failed.");
        }
    }

    private static class LazyHolder {
        private static final DatabaseConnection instance = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return LazyHolder.instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException _) {
            System.err.println("Error getting connection.");
        }

        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException _) {
                System.err.println("Error closing connection.");
            }
        }
    }
}
