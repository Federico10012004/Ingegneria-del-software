package it.calcettohub.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
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
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException _) {
            System.err.println("Connection failed.");
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
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
