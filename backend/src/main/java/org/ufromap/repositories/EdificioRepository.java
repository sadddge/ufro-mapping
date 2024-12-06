package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import lombok.extern.java.Log;
import org.ufromap.core.base.BaseRepository;
import org.ufromap.core.exceptions.InternalErrorException;
import org.ufromap.feature.buildings.models.Edificio;
@Log
public class EdificioRepository extends BaseRepository<Edificio> {

    public EdificioRepository() {
        super();
    }

    public EdificioRepository(Connection connection) {
        super(connection);
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
        Edificio edificio = new Edificio();
        try {
            edificio.setId(resultSet.getInt("id"));
            edificio.setNombre(resultSet.getString("nombre_edificio"));
            edificio.setAlias(resultSet.getString("alias"));
            edificio.setTipo(resultSet.getString("tipo"));
            edificio.setLatitud(resultSet.getFloat("latitud"));
            edificio.setLongitud(resultSet.getFloat("longitud"));
        } catch (SQLException e) {
            log.log(Level.SEVERE,"Error mapping object", e);
            throw new InternalErrorException("Error mapping object");
        }
        return edificio;
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
