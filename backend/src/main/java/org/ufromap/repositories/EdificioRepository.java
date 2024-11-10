package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;

/**
 * Repositorio para manejar las operaciones relacionadas con la entidad {@link Edificio}.
 * Provee métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la tabla 'edificio'.
 */
public class EdificioRepository extends BaseRepository<Edificio> {

    private final SalaRepository salaRepository;

    /**
     * Constructor por defecto que inicializa una instancia de {@link SalaRepository}.
     */
    public EdificioRepository() {
        super();
        this.salaRepository = new SalaRepository();
    }

    public EdificioRepository(Connection connection, SalaRepository salaRepository) {
        super(connection);
        this.salaRepository = salaRepository;
    }


    @Override
    protected String getTableName() {
        return "edificio";
    }

    @Override
    protected String getColumns() {
        return "id, nombre_edificio, alias, tipo, latitud, longitud";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO edificio (nombre_edificio, alias, tipo, latitud, longitud) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE edificio SET nombre_edificio = ?, alias = ?, tipo = ?, latitud = ?, longitud = ? WHERE id = ?";
    }

    @Override
    public Edificio mapToObject(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre_edificio");
            String alias = resultSet.getString("alias");
            String tipo = resultSet.getString("tipo");
            float latitud = resultSet.getFloat("latitud");
            float longitud = resultSet.getFloat("longitud");
            List<Sala> salas = salaRepository.findByEdificioId(id);

            return new Edificio(id, nombre, alias, tipo, latitud, longitud, salas);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Edificio obj) throws SQLException {
        statement.setString(1, obj.getNombre());
        statement.setString(2, obj.getAlias());
        statement.setString(3, obj.getTipo());
        statement.setFloat(4, obj.getLatitud());
        statement.setFloat(5, obj.getLongitud());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Edificio obj) throws SQLException {
        setParametersForInsert(statement, obj);
        statement.setInt(6, obj.getId());
    }
}
