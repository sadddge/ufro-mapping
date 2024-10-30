package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;

/**
 * Repositorio para manejar las operaciones relacionadas con la entidad {@link Edificio}.
 * Provee métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la tabla 'edificio'.
 */
public class EdificioRepository {

    private final SalaRepository salaRepository;

    /**
     * Constructor por defecto que inicializa una instancia de {@link SalaRepository}.
     */
    public EdificioRepository() {
        this.salaRepository = new SalaRepository();
    }

    /**
     * Constructor que permite inyectar una instancia de {@link SalaRepository}.
     *
     * @param salaRepository Instancia del repositorio de salas.
     */
    public EdificioRepository(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    /**
     * Obtiene todos los edificios registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Edificio} con los detalles de cada edificio y sus salas.
     */
    public List<Edificio> getEdificios() {
        List<Edificio> edificios = new ArrayList<>();
        String query = "SELECT * FROM edificio";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Query: " + query);

            while (resultSet.next()) {
                System.out.println("result:" + resultSet);
                int id = resultSet.getInt("edificio_id");
                String nombre = resultSet.getString("nombre_edificio");
                String alias = resultSet.getString("alias_edificio");
                String tipo = resultSet.getString("tipo_edificio");
                float latitud = resultSet.getFloat("latitud");
                float longitud = resultSet.getFloat("longitud");
                System.out.println("id: " + id);
                System.out.println("nombre: " + nombre);
                System.out.println("alias: " + alias);
                System.out.println("tipo: " + tipo);
                System.out.println("latitud: " + latitud);
                System.out.println("longitud: " + longitud);

                edificios.add(new Edificio(id, nombre, alias, tipo, latitud, longitud, new ArrayList<>()));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return edificios;
    }

    /**
     * Obtiene un edificio específico por su ID.
     *
     * @param id El ID del edificio a buscar.
     * @return Un objeto {@link Edificio} con los detalles del edificio, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioById(int id) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("edificio");
                    String alias = resultSet.getString("alias");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, tipo, latitud, longitud, salas);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
        return edificio;
    }

    /**
     * Busca un edificio por su nombre.
     *
     * @param nombre El nombre del edificio.
     * @return Un objeto {@link Edificio} con los detalles del edificio, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByNombre(String nombre) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE edificio = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String alias = resultSet.getString("alias");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, tipo, latitud, longitud, salas);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
        return edificio;
    }

    /**
     * Busca un edificio por su alias.
     *
     * @param alias El alias del edificio.
     * @return Un objeto {@link Edificio} con los detalles del edificio, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByAlias(String alias) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE alias = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, alias);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String nombre = resultSet.getString("edificio");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, tipo, latitud, longitud, salas);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
        return edificio;
    }

    /**
     * Busca un edificio por su tipo.
     *
     * @param tipo El tipo del edificio (por ejemplo, académico, administrativo, etc.).
     * @return Un objeto {@link Edificio} con los detalles del edificio, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByTipo(String tipo) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE tipo = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String nombre = resultSet.getString("edificio");
                    String alias = resultSet.getString("alias");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, tipo, latitud, longitud, salas);
                }
            }
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
        }
        return edificio;
    }

    /**
     * Agrega un nuevo edificio a la base de datos.
     *
     * @param edificio El objeto {@link Edificio} que contiene los datos del edificio a agregar.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean addEdificio(Edificio edificio) {
        String query = "INSERT INTO edificio (edificio, alias, latitud, longitud, tipo) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setFloat(3, edificio.getLatitud());
            statement.setFloat(4, edificio.getLongitud());
            statement.setString(5, edificio.getTipo());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            return false;
        }
    }

    /**
     * Actualiza los datos de un edificio existente.
     *
     * @param edificio El objeto {@link Edificio} con los nuevos datos.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean updateEdificio(Edificio edificio) {
        String query = "UPDATE edificio SET edificio = ?, alias = ?, latitud = ?, longitud = ?, tipo = ? WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setFloat(3, edificio.getLatitud());
            statement.setFloat(4, edificio.getLongitud());
            statement.setString(5, edificio.getTipo());
            statement.setInt(6, edificio.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            return false;
        }
    }

    /**
     * Elimina un edificio de la base de datos por su ID.
     *
     * @param id El ID del edificio a eliminar.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean deleteEdificio(int id) {
        String query = "DELETE FROM edificio WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            return false;
        }
    }
}
