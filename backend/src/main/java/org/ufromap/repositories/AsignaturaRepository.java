package org.ufromap.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ufromap.models.Asignatura;
import org.ufromap.models.Clase;


/**
 * Clase repositorio para manejar las operaciones relacionadas con la entidad "Asignatura" en la base de datos.
 * Proporciona métodos para recuperar, agregar, actualizar y eliminar registros de "Asignatura",
 * así como para obtener las "Clase" relacionadas.
 */
public class AsignaturaRepository extends BaseRepository<Asignatura> {

    private final ClaseRepository clasesRepository;

    /**
     * Constructor por defecto que inicializa el repositorio con una nueva instancia de ClaseRepository.
     */
    public AsignaturaRepository() {
        this.clasesRepository = new ClaseRepository();
    }


    @Override
    protected String getTableName() {
        return "asignatura";
    }

    @Override
    protected String getColumns() {
        return "id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO asignatura (nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE asignatura SET nombre_asignatura = ?, codigo_asignatura = ?, descripcion_asignatura = ?, sct_asignatura = ? WHERE id = ?";
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


}