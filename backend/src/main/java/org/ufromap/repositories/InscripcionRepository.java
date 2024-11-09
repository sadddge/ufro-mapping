package org.ufromap.repositories;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;
import org.ufromap.models.Inscripcion;
import org.ufromap.models.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InscripcionRepository extends BaseRepository<Inscripcion> {

    private static final Logger log = Logger.getLogger(InscripcionRepository.class.getName());

    public List<Inscripcion> getInscripcionByUsuarioId(int usuarioId){
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT id, asignatura_id FROM inscribe WHERE usuario_id = ?";
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


    @Override
    protected String getTableName() {
        return "inscribe";
    }

    @Override
    protected String getColumns() {
        return "usuario_id, asignatura_id";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO inscribe (usuario_id, asingatura_id) VALUES (?, ?)";
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

    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Inscripcion obj) throws SQLException {

    }
}
