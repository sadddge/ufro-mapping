package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Clase;



/**
 * Clase repositorio para manejar las operaciones relacionadas con la entidad "Asignatura" en la base de datos.
 * Proporciona métodos para recuperar, agregar, actualizar y eliminar registros de "Asignatura",
 * así como para obtener las "Clase" relacionadas.
 */
public class AsignaturaRepository implements IRepository<Asignatura> {

    private final ClaseRepository clasesRepository;

    /**
     * Constructor que inicializa el repositorio con una instancia de ClaseRepository.
     * @param clasesRepository el repositorio para manejar las operaciones relacionadas con Clase.
     */
    public AsignaturaRepository(ClaseRepository clasesRepository) {
        this.clasesRepository = clasesRepository;
    }

    /**
     * Constructor por defecto que inicializa el repositorio con una nueva instancia de ClaseRepository.
     */
    public AsignaturaRepository() {
        this.clasesRepository = new ClaseRepository();
    }

    /**
     * Obtiene todas las asignaturas registradas en la base de datos.
     * @return Una lista de objetos Asignatura que contiene los detalles de cada asignatura.
     */
    @Override
    public List<Asignatura> findAll() {
        List<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                asignaturas.add(mapToObject(resultSet));
            }

        } catch (SQLException e) {
            return null;
        }
        return asignaturas;
    }

    /**
     * Obtiene una asignatura específica por su ID.
     * @param id El ID de la asignatura a buscar.
     * @return El objeto Asignatura correspondiente al ID proporcionado, o null si no se encuentra.
     */
    @Override
    public Asignatura findById(int id) {
        Asignatura asignatura = null;
        String query = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura WHERE asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    asignatura = mapToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return asignatura;
    }


    @Override
    public List<Asignatura> findByFilter(Map<String, Object> filters) {
        List<Asignatura> asignaturas = new ArrayList<>();
        StringBuilder query = getQuery(filters);
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query.toString());
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                asignaturas.add(mapToObject(resultSet));
            }
        } catch (SQLException e) {
            return null;
        }
        return asignaturas;
    }

    private StringBuilder getQuery(Map<String, Object> filters) {
        StringBuilder query = new StringBuilder("SELECT * FROM asignatura WHERE 1=1");

        if (filters.containsKey("nombre")) query.append(" AND nombre_asignatura = ?");
        if (filters.containsKey("codigo")) query.append(" AND codigo_asignatura = ?");
        if (filters.containsKey("descripcion")) query.append(" AND descripcion_asignatura = ?");
        if (filters.containsKey("sct")) query.append(" AND sct_asignatura = ?");

        return query;
    }

    @Override
    public Asignatura add(Asignatura obj) {
        String query = "INSERT INTO asignatura (nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura) VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, obj.getNombre());
            statement.setString(2, obj.getCodigo());
            statement.setString(3, obj.getDescripcion());
            statement.setInt(4, obj.getSct());
            statement.executeUpdate();
            obj.setId(DatabaseConnection.getLastInsertId());
            return obj;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Asignatura update(Asignatura obj) {
        String query = "UPDATE asignatura SET nombre_asignatura = ?, codigo_asignatura = ?, descripcion_asignatura = ?, sct_asignatura = ? WHERE asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, obj.getNombre());
            statement.setString(2, obj.getCodigo());
            statement.setString(3, obj.getDescripcion());
            statement.setInt(4, obj.getSct());
            statement.setInt(5, obj.getId());
            statement.executeUpdate();
            return obj;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM asignatura WHERE asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Asignatura mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("asignatura_id");
            String nombre = resultSet.getString("nombre_asignatura");
            String codigo = resultSet.getString("codigo_asignatura");
            String descripcion = resultSet.getString("descripcion_asignatura");
            int sct = resultSet.getInt("sct_asignatura");
            List<Clase> clases = clasesRepository.findByAsignaturaId(id);
            return new Asignatura(id, nombre, codigo, descripcion, sct, clases);
        } catch (SQLException e) {
            return null;
        }
    }


}