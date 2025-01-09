package org.ufromap.repositories;

import lombok.extern.java.Log;
import org.ufromap.core.base.BaseRepository;
import org.ufromap.core.config.DatabaseConnection;
import org.ufromap.core.exceptions.InternalErrorException;
import org.ufromap.feature.courses.models.Asignatura;
import org.ufromap.feature.users.models.Inscripcion;

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

    public void deleteByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId){
        String query = "DELETE FROM inscribe WHERE usuario_id = ? AND asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, usuarioId);
            statement.setInt(2, asignaturaId);
            statement.executeUpdate();
        }catch (SQLException e){
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Error deleting inscripcion");
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
        Inscripcion inscripcion = new Inscripcion();
        try {
            inscripcion.setId(resultSet.getInt("id"));
            inscripcion.setUsuarioId(resultSet.getInt("usuario_id"));
            inscripcion.setAsignaturaId(resultSet.getInt("asignatura_id"));
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error mapping object", e);
            throw new InternalErrorException("Error mapping object");
        }
        return inscripcion;
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

    public List<Asignatura> findAsignaturasByUsuarioId(int usuarioId){
        List<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT a.id, a.nombre_asignatura, a.codigo, a.sct FROM asignatura a JOIN inscribe i ON a.id = i.asignatura_id WHERE i.usuario_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, usuarioId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Asignatura asignatura = new Asignatura();
                    asignatura.setId(resultSet.getInt("id"));
                    asignatura.setNombre(resultSet.getString("nombre_asignatura"));
                    asignatura.setCodigo(resultSet.getString("codigo"));
                    asignatura.setSct(resultSet.getInt("sct"));
                    asignaturas.add(asignatura);
                }
            }
        }catch (SQLException e){
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Error getting asignaturas");
        }
        return asignaturas;
    }

    public boolean existsByUsuarioIdAndAsignaturaId(int usuarioId, int asignaturaId){
        String query = "SELECT COUNT(*) FROM inscribe WHERE usuario_id = ? AND asignatura_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, usuarioId);
            statement.setInt(2, asignaturaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }catch (SQLException e){
            log.log(Level.SEVERE, "Error executing query: " + query, e);
            throw new InternalErrorException("Error getting asignaturas");
        }
        return false;
    }
}
