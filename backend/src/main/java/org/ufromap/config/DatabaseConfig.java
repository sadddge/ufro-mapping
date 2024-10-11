package org.ufromap.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que gestiona la configuración de la base de datos mediante la carga de un archivo
 * de propiedades. Proporciona métodos para obtener las configuraciones como la URL,
 * nombre de usuario, contraseña y driver de la base de datos.
 */
public class DatabaseConfig {
    private Properties properties;

    /**
     * Constructor de la clase DatabaseConfig.
     *
     * Carga el archivo de configuración `config.properties` desde el sistema de archivos.
     * Si el archivo no se encuentra o no puede ser leído, se imprime una traza de la excepción.
     */
    public DatabaseConfig() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la URL de conexión de la base de datos desde el archivo de propiedades.
     *
     * @return La URL de la base de datos como una cadena de texto.
     */
    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    /**
     * Obtiene el nombre de usuario para acceder a la base de datos desde el archivo de propiedades.
     *
     * @return El nombre de usuario de la base de datos como una cadena de texto.
     */
    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    /**
     * Obtiene la contraseña para acceder a la base de datos desde el archivo de propiedades.
     *
     * @return La contraseña de la base de datos como una cadena de texto.
     */
    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    /**
     * Obtiene el nombre del driver JDBC para la conexión a la base de datos desde el archivo de propiedades.
     *
     * @return El nombre del driver de la base de datos como una cadena de texto.
     */
    public String getDatabaseDriver() {
        return properties.getProperty("db.driver");
    }
}
