package org.ufromap.repositories;

import lombok.extern.java.Log;
import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Inscripcion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
@Log
public class InscripcionRepository extends BaseRepository<Inscripcion> {

    public InscripcionRepository() {
        super();
    }

    public InscripcionRepository(Connection connection) {
        super(connection);
    }

    public List<Inscripcion> getInscripcionByUsuarioId(int usuarioId){
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT id, usuario_id, asignatura_id FROM inscribe WHERE usuario_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, usuarioId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Inscripcion inscripcion = mapToObject(resultSet);
                    inscripciones.add(inscripcion);
                }
            }
        }catch (SQLException e){
            log.log(Level.SEVERE, "Error executing query: " + query, e);
        }
        return inscripciones;
    }

    public void deleteInscripcionByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId){
        String query = "DELETE FROM inscribe WHERE usuario_id = ? AND asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, usuarioId);
            statement.setInt(2, asignaturaId);
            statement.executeUpdate();
        }catch (SQLException e){
            log.log(Level.SEVERE, "Error executing query: " + query, e);
        }
    }


    @Override
    protected String getTableName() {
        return "inscribe";
    }

    @Override
    protected String getColumns() {
        return "id, usuario_id, asignatura_id";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO inscribe (usuario_id, asignatura_id) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE inscribe SET asignatura_id = ?, usuario_id = ? WHERE id = ?";
    }

    @Override
    protected Inscripcion mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int usuarioId = resultSet.getInt("usuario_id");
            int asignaturaId = resultSet.getInt("asignatura_id");
            return new Inscripcion(id, usuarioId, asignaturaId);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Inscripcion obj) throws SQLException {
        statement.setInt(1, obj.getUsuarioId());
        statement.setInt(2, obj.getAsignaturaId());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Inscripcion obj) throws SQLException {
        statement.setInt(1, obj.getAsignaturaId());
        statement.setInt(2, obj.getUsuarioId());
        statement.setInt(3, obj.getId());
    }
}
