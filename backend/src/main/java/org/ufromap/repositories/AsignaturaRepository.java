package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lombok.extern.java.Log;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Inscripcion;
@Log
public class AsignaturaRepository extends BaseRepository<Asignatura> {

    public AsignaturaRepository() {
        super();
    }

    public AsignaturaRepository(Connection connection) {
        super(connection);
    }


    @Override
    protected String getTableName() {
        return "asignatura";
    }

    @Override
    protected String getColumns() {
        return "id, nombre_asignatura, codigo, descripcion, sct";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO asignatura (nombre_asignatura, codigo, descripcion, sct) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE asignatura SET nombre_asignatura = ?, codigo = ?, descripcion = ?, sct = ? WHERE id = ?";
    }

    @Override
    public Asignatura mapToObject(ResultSet resultSet) {
        Asignatura asignatura = new Asignatura();
        try {
            asignatura.setId(resultSet.getInt("id"));
            asignatura.setNombre(resultSet.getString("nombre_asignatura"));
            asignatura.setCodigo(resultSet.getString("codigo"));
            asignatura.setDescripcion(resultSet.getString("descripcion"));
            asignatura.setSct(resultSet.getInt("sct"));
        } catch (SQLException e) {
            log.log(java.util.logging.Level.SEVERE, "Error mapping object", e);
        }
        return asignatura;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Asignatura obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getCodigo());
        statement.setString(3, obj.getDescripcion());
        statement.setInt(4, obj.getSct());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Asignatura obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getCodigo());
        statement.setString(3, obj.getDescripcion());
        statement.setInt(4, obj.getSct());
        statement.setInt(5, obj.getId());
    }

    public List<Asignatura> findByInscripciones(List<Inscripcion> inscripciones) {
        List<Asignatura> asignaturas = findAll();
        asignaturas.removeIf(asignatura -> inscripciones.stream().noneMatch(inscripcion -> inscripcion.getAsignaturaId() == asignatura.getId()));
        return asignaturas;
    }


}