package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;

/**
 * Clase que implementa los métodos para gestionar la entidad Clase en la base de datos.
 * Provee funciones para obtener, agregar, actualizar y eliminar clases.
 */
public class ClaseRepository extends BaseRepository<Clase> {

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

    @Override
    protected String getTableName() {
        return "clase";
    }

    @Override
    protected String getColumns() {
        return "id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO clase (sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE clase SET sala_id = ?, edificio_id = ?, asignatura_id = ?, dia_semana = ?, periodo_clase = ?, docente_nombre = ?, modulo = ? WHERE id = ?";
    }

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

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Clase obj) throws SQLException {
        statement.setInt(1, obj.getSalaId());
        statement.setInt(2, obj.getEdificioId());
        statement.setInt(3, obj.getAsignaturaId());
        statement.setInt(4, obj.getDiaSemana());
        statement.setInt(5, obj.getPeriodo());
        statement.setString(6, obj.getDocente());
        statement.setInt(7, obj.getModulo());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Clase obj) throws SQLException {
        setParametersForInsert(statement, obj);
        statement.setInt(8, obj.getId());
    }


}