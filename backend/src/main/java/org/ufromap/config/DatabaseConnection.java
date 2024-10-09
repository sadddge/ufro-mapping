package org.ufromap.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                DatabaseConfig config = new DatabaseConfig();
                String url = config.getDatabaseUrl();
                String username = config.getDatabaseUsername();
                String password = config.getDatabasePassword();
                String driver = config.getDatabaseDriver();

                Class.forName(driver);
                
                connection = DriverManager.getConnection(url, username, password);

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
