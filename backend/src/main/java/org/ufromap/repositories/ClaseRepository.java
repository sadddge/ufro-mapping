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

/**
 * Clase que implementa los métodos para gestionar la entidad Clase en la base de datos.
 * Provee funciones para obtener, agregar, actualizar y eliminar clases.
 */
public class ClaseRepository implements IRepository<Clase> {

    /**
     * Obtiene todas las clases registradas en la base de datos.
     * @return Una lista de objetos Clase que contiene los detalles de cada clase.
     */
    @Override
    public List<Clase> findAll() {
        List<Clase> clases = new ArrayList<>();
        String query = "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Clase clase = mapToObject(resultSet);
                clases.add(clase);
            }
        } catch (SQLException e) {
            return null;
        }
        return clases;
    }

    /**
     * Obtiene una clase específica por su ID.
     * @param id El ID de la clase a buscar.
     * @return El objeto Clase correspondiente al ID proporcionado, o null si no se encuentra.
     */
    @Override
    public Clase findById(int id) {
        Clase clase = null;
        String query = "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE clase_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    clase = mapToObject(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return clase;
    }

    /**
     * Obtiene una lista de clases filtradas por los parámetros proporcionados.
     * @param filter Un mapa con los campos y valores a filtrar.
     * @return Una lista de objetos Clase que cumplen con los criterios de filtrado.
     */
    @Override
    public List<Clase> findByFilter(Map<String, Object> filter) {
        List<Clase> clases = new ArrayList<>();
        StringBuilder query = getQuery(filter);

        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (filter.containsKey("sala_id")) statement.setInt(index++, (int) filter.get("sala_id"));
            if (filter.containsKey("edificio_id")) statement.setInt(index++, (int) filter.get("edificio_id"));
            if (filter.containsKey("asignatura_id")) statement.setInt(index++, (int) filter.get("asignatura_id"));
            if (filter.containsKey("dia_semana")) statement.setString(index++, (String) filter.get("dia_semana"));
            if (filter.containsKey("periodo_clase")) statement.setString(index++, (String) filter.get("periodo_clase"));
            if (filter.containsKey("docente_nombre")) statement.setString(index++, (String) filter.get("docente_nombre"));
            if (filter.containsKey("modulo")) statement.setString(index, (String) filter.get("modulo"));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Clase clase = mapToObject(resultSet);
                    clases.add(clase);
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return clases;
    }

    public List<Clase> findByAsignaturaId(int asignaturaId) {
        String query = "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE asignatura_id = ?";
        return findByParameter(query, asignaturaId);
    }

    public List<Clase> findBySalaId(int id) {
        String query = "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE sala_id = ?";
        return findByParameter(query, id);
    }

    private List<Clase> findByParameter(String query, int param) {
        List<Clase> clases = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Clase clase = mapToObject(resultSet);
                    clases.add(clase);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return clases;
    }

    private static StringBuilder getQuery(Map<String, Object> filter) {
        StringBuilder query = new StringBuilder("SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE 1=1");

        if (filter.containsKey("sala_id")) query.append(" AND sala_id = ?");
        if (filter.containsKey("edificio_id")) query.append(" AND edificio_id = ?");
        if (filter.containsKey("asignatura_id")) query.append(" AND asignatura_id = ?");
        if (filter.containsKey("dia_semana")) query.append(" AND dia_semana = ?");
        if (filter.containsKey("periodo_clase")) query.append(" AND periodo_clase = ?");
        if (filter.containsKey("docente_nombre")) query.append(" AND docente_nombre = ?");
        if (filter.containsKey("modulo")) query.append(" AND modulo = ?");
        return query;
    }

    /**
     * Agrega una nueva clase a la base de datos.
     * @param obj El objeto Clase con los datos de la nueva clase.
     */
    @Override
    public Clase add(Clase obj) {
        String query = "INSERT INTO clase (sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setClaseParameters(statement, obj);
            statement.executeUpdate();
            obj.setId(DatabaseConnection.getLastInsertId());
            return obj;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Actualiza los datos de una clase existente.
     * @param obj El objeto Clase con los datos actualizados.
     */
    @Override
    public Clase update(Clase obj) {
        String query = "UPDATE clase SET sala_id = ?, edificio_id = ?, asignatura_id = ?, dia_semana = ?, periodo_clase = ?, docente_nombre = ?, modulo = ? WHERE clase_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setClaseParameters(statement, obj);
            statement.setInt(8, obj.getId());
            statement.executeUpdate();
            return obj;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Elimina una clase de la base de datos por su ID.
     * @param id El ID de la clase a eliminar.
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM clase WHERE clase_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void setClaseParameters(PreparedStatement statement, Clase obj) throws SQLException {
        statement.setInt(1, obj.getSalaId());
        statement.setInt(2, obj.getEdificioId());
        statement.setInt(3, obj.getAsignaturaId());
        statement.setInt(4, obj.getDiaSemana());
        statement.setInt(5, obj.getPeriodo());
        statement.setString(6, obj.getDocente());
        statement.setInt(7, obj.getModulo());
    }

    /**
     * Mapea un ResultSet a un objeto Clase.
     * @param resultSet El ResultSet que contiene los datos de la clase.
     * @return Un objeto Clase con los datos obtenidos del ResultSet.
     */
    @Override
    public Clase mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("clase_id");
            int salaId = resultSet.getInt("sala_id");
            int edificioId = resultSet.getInt("edificio_id");
            int asignaturaId = resultSet.getInt("asignatura_id");
            int diaSemana = resultSet.getInt("dia_semana");
            int periodo = resultSet.getInt("periodo_clase");
            String docente = resultSet.getString("docente_nombre");
            int modulo = resultSet.getInt("modulo");
            return new Clase(id, salaId, edificioId, asignaturaId, diaSemana, periodo, docente, modulo);
        } catch (SQLException e) {
            return null;
        }
    }


}