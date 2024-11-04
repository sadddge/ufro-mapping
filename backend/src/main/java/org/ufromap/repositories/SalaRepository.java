package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;
import org.ufromap.models.Sala;

/**
 * Repositorio que gestiona las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad {@link Sala}.
 * Se conecta a la base de datos y ejecuta consultas SQL para manipular los datos de la tabla "sala".
 */
public class SalaRepository implements IRepository<Sala> {

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
     * Obtiene todas las salas registradas en la base de datos.
     *
     * @return Una lista de objetos {@link Sala} que contiene los detalles de cada sala.
     */
    @Override
    public List<Sala> findAll() {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT sala_id, edificio_id, nombre_sala FROM sala";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Sala sala = mapToObject(resultSet);
                salas.add(sala);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return salas;
    }

    /**
     * Obtiene una sala específica por su ID.
     *
     * @param id El ID de la sala a buscar.
     * @return El objeto {@link Sala} correspondiente al ID proporcionado, o {@code null} si no se encuentra.
     */
    @Override
    public Sala findById(int id) {
        Sala sala = null;
        String query = "SELECT sala_id, edificio_id, nombre_sala  FROM sala WHERE sala_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    sala = mapToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sala;
    }

    public List<Sala> findByEdificioId(int edificioId) {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT sala_id, edificio_id, nombre_sala FROM sala WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, edificioId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Sala sala = mapToObject(resultSet);
                    salas.add(sala);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return salas;
    }

    /**
     * Obtiene una lista de salas filtradas por los parámetros proporcionados.
     *
     * @param filters Un mapa con los nombres de las columnas y los valores a filtrar.
     * @return Una lista de objetos {@link Sala} que coinciden con los parámetros de búsqueda.
     */
    @Override
    public List<Sala> findByFilter(Map<String, Object> filters) {
        List<Sala> salas = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT sala_id, edificio_id, nombre_sala FROM sala WHERE 1=1");

        if (filters.containsKey("nombre")) query.append(" AND nombre_sala = ?");
        if (filters.containsKey("edificioId")) query.append(" AND edificio_id = ?");


        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (filters.containsKey("nombre")) statement.setString(index++, (String) filters.get("nombre"));
            if (filters.containsKey("edificioId")) statement.setInt(index, (int) filters.get("edificioId"));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Sala sala = mapToObject(resultSet);
                    salas.add(sala);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return salas;
    }

    /**
     * Agrega una nueva sala a la base de datos.
     *
     * @param obj El objeto {@link Sala} con los datos de la nueva sala.
     */
    @Override
    public Sala add(Sala obj) {
        String query = "INSERT INTO sala (edificio_id, nombre_sala) VALUES (?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, obj.getEdificioId());
            statement.setString(2, obj.getNombre());
            statement.executeUpdate();
            obj.setId(DatabaseConnection.getLastInsertId());
            return obj;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza los datos de una sala existente.
     *
     * @param obj El objeto {@link Sala} con los datos actualizados.
     */
    @Override
    public Sala update(Sala obj) {
        String query = "UPDATE sala SET edificio_id = ?, nombre_sala = ? WHERE sala_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, obj.getEdificioId());
            statement.setString(2, obj.getNombre());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
            return obj;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Elimina una sala de la base de datos por su ID.
     *
     * @param id El ID de la sala a eliminar.
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM sala WHERE sala_id = ?";
        return execDelete(query, id);
    }

    public boolean deleteByEdificioId(int edificioId) {
        String query = "DELETE FROM sala WHERE edificio_id = ?";
        return execDelete(query, edificioId);
    }

    private boolean execDelete(String query, int param) {
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, param);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mapea una fila de la tabla "sala" en la base de datos a un objeto {@link Sala}.
     *
     * @param resultSet El conjunto de resultados de una consulta SQL.
     * @return Un objeto {@link Sala} con los datos de la fila proporcionada.
     */
    @Override
    public Sala mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("sala_id");
            int edificioId = resultSet.getInt("edificio_id");
            String nombre = resultSet.getString("nombre_sala");
            List<Clase> clases = claseRepository.findBySalaId(id);

            return new Sala(id, edificioId, nombre, clases);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
