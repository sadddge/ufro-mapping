package org.ufromap.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    private Properties properties;

    public DatabaseConfig() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    public String getDatabaseDriver() {
        return properties.getProperty("db.driver");
    }
}
