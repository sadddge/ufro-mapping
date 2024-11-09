package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;
import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;

/**
 * Repositorio que gestiona las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad {@link Sala}.
 * Se conecta a la base de datos y ejecuta consultas SQL para manipular los datos de la tabla "sala".
 */
public class SalaRepository {

    private final ClaseRepository claseRepository;
    private final EdificioRepository edificioRepository;

    /**
     * Constructor por defecto que inicializa el repositorio con instancias de los servicios
     * de {@link ClaseRepository} y {@link EdificioRepository}.
     */
    public SalaRepository() {
        this.claseRepository = new ClaseRepository();
        this.edificioRepository = new EdificioRepository();
    }

    /**
     * Constructor que permite inyectar instancias personalizadas de {@link ClaseRepository}
     * y {@link EdificioRepository}.
     *
     * @param claseRepository    El repositorio para gestionar las clases.
     * @param edificioRepository El repositorio para gestionar los edificios.
     */
    public SalaRepository(ClaseRepository claseRepository, EdificioRepository edificioRepository) {
        this.claseRepository = claseRepository;
        this.edificioRepository = edificioRepository;
    }

    /**
     * Obtiene todas las salas almacenadas en la base de datos.
     *
     * @return Una lista de salas.
     * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
     */
    public List<Sala> getSalas() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT * FROM sala";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int edificioId = resultSet.getInt("edificio_id");
                String nombre = resultSet.getString("nombre_sala");

                Sala sala = new Sala(id, edificioId, nombre);
                salas.add(sala);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salas;
    }

    /**
     * Obtiene una sala por su ID.
     *
     * @param id El identificador de la sala.
     * @return La sala correspondiente al ID proporcionado, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
     */
    public Sala getSalaById(int id) throws SQLException {
        Sala sala = null;
        String query = "SELECT * FROM sala WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int edificioId = resultSet.getInt("edificio_id");
                    String nombre = resultSet.getString("nombre_sala");
                    sala = new Sala(id, edificioId, nombre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sala;
    }

    /**
     * Obtiene todas las salas que pertenecen a un edificio específico por el ID del edificio.
     *
     * @param id El identificador del edificio.
     * @return Una lista de salas asociadas al edificio.
     * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
     */
    public List<Sala> getSalasByEdificioId(int edificioId) throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT * FROM sala WHERE edificio_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, edificioId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");

                    String nombre = resultSet.getString("nombre_sala");
                    Sala sala = new Sala(id, edificioId, nombre);
                    salas.add(sala);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salas;
    }

    /**
     * Obtiene una sala por su nombre.
     *
     * @param nombre El nombre de la sala.
     * @return La sala con el nombre especificado, o null si no existe.
     * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
     */
    public Sala getSalaByNombre(String nombre) throws SQLException {
        Sala sala = null;
        String query = "SELECT * FROM sala WHERE nombre_sala = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int edificioId = resultSet.getInt("edificio_id");
                    sala = new Sala(id, edificioId, nombre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sala;
    }

    /**
     * Agrega una nueva sala a la base de datos.
     *
     * @param sala La sala que se desea agregar.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la inserción en la base de datos.
     */
    public boolean addSala(Sala sala) throws SQLException {
        String query = "INSERT INTO sala (edificio_id, nombre_sala) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sala.getId());
            preparedStatement.setInt(2, sala.getEdificioId());
            preparedStatement.setString(3, sala.getNombre());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza una sala existente en la base de datos.
     *
     * @param sala La sala que se desea actualizar.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la actualización en la base de datos.
     */
    public boolean updateSala(Sala sala) throws SQLException {
        String query = "UPDATE sala SET sala = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sala.getId());
            preparedStatement.setInt(2, sala.getEdificioId());
            preparedStatement.setString(3, sala.getNombre());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una sala de la base de datos por su ID.
     *
     * @param id El identificador de la sala que se desea eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la eliminación en la base de datos.
     */
    public boolean deleteSala(long id) throws SQLException {
        String query = "DELETE FROM sala WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
