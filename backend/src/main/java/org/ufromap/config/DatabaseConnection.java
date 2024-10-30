package org.ufromap.config;

import java.sql.*;

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

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Asigna null para que se pueda volver a abrir si es necesario
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static int getLastInsertId() {
        int lastId = -1;
        try {
            String query = "SELECT LAST_INSERT_ID()";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lastId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }
}
