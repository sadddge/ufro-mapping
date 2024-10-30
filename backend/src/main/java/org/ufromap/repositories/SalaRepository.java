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

    /**
     * Constructor por defecto que inicializa el repositorio con instancias de los servicios
     * de {@link ClaseRepository}.
     */
    public SalaRepository() {
        this.claseRepository = new ClaseRepository();
    }

    /**
     * Constructor que permite inyectar instancias personalizadas de {@link ClaseRepository}
     * y {@link EdificioRepository}.
     *
     * @param claseRepository    El repositorio para gestionar las clases.
     */
    public SalaRepository(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
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
                int id = resultSet.getInt("sala_id");
                String nombre = resultSet.getString("sala");
                int edificio_id = resultSet.getInt("edificio_id");
                List<Clase> clases = claseRepository.getClasesBySalaId(id);
                Sala sala = new Sala(id, edificio_id, nombre, clases);
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
        String query = "SELECT * FROM sala WHERE sala_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("sala");
                    int edificio_id = resultSet.getInt("edificio_id");
                    List<Clase> clases = claseRepository.getClasesBySalaId(id);
                    sala = new Sala(id, edificio_id, nombre, clases);
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
    public List<Sala> getSalasByEdificioId(int id) throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT * FROM sala WHERE edificio_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int salaId = resultSet.getInt("sala_id");
                    String nombre = resultSet.getString("nombre_sala");
                    List<Clase> clases = claseRepository.getClasesBySalaId(salaId);
                    Sala sala = new Sala(salaId, id, nombre, clases);
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
        String query = "SELECT * FROM sala WHERE sala = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("sala_id");
                    int edificio_id = resultSet.getInt("edificio_id");
                    List<Clase> clases = claseRepository.getClasesBySalaId(id);
                    sala = new Sala(id, edificio_id, nombre, clases);
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
        String query = "INSERT INTO sala (edificio_id, sala) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sala.getEdificioId());
            preparedStatement.setString(2, sala.getNombre());
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
        String query = "UPDATE sala SET sala = ? WHERE sala_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, sala.getNombre());
            preparedStatement.setLong(2, sala.getEdificioId());
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
        String query = "DELETE FROM sala WHERE sala_id = ?";

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
