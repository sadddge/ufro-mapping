package org.ufromap.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos utilizando las configuraciones
 * proporcionadas por la clase {@link DatabaseConfig}. Implementa el patrón Singleton
 * para asegurar que solo se mantenga una única instancia de la conexión a la base de datos.
 */
public class DatabaseConnection {
    private static Connection connection = null;

    /**
     * Obtiene la conexión a la base de datos. Si no existe una conexión activa,
     * se crea una nueva utilizando las configuraciones proporcionadas en `config.properties`.
     *
     * @return La conexión activa a la base de datos.
     */
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
