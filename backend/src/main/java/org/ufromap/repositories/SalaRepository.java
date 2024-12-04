package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;
import org.ufromap.core.base.BaseRepository;
import org.ufromap.feature.classrooms.models.Sala;
@Log
public class SalaRepository extends BaseRepository<Sala> {

    private final ClaseRepository claseRepository;
    public SalaRepository() {
        this.claseRepository = new ClaseRepository();
    }
    public SalaRepository(Connection connection, ClaseRepository claseRepository) {
        super(connection);
        this.claseRepository = claseRepository;
    }


    public List<Sala> findByEdificioId(int edificioId) {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT id, edificio_id, nombre_sala FROM sala WHERE edificio_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, edificioId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Sala sala = mapToObject(resultSet);
                    salas.add(sala);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return salas;
    }

    @Override
    protected String getTableName() {
        return "sala";
    }

    @Override
    protected String getColumns() {
        return "id, edificio_id, nombre_sala";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO sala (edificio_id, nombre_sala) VALUES (?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE sala SET edificio_id = ?, nombre_sala = ? WHERE id = ?";
    }

    @Override
    public Sala mapToObject(ResultSet resultSet) {
        Sala sala = new Sala();
        try {
            sala.setId(resultSet.getInt("id"));
            sala.setEdificioId(resultSet.getInt("edificio_id"));
            sala.setNombre(resultSet.getString("nombre_sala"));
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error mapping object", e);
        }
        return sala;
    }

    @Override
    protected void setParametersForInsert(PreparedStatement statement, Sala obj) throws SQLException {
        statement.setInt(1, obj.getEdificioId());
        statement.setString(2, obj.getNombre());
    }

    @Override
    protected void setParametersForUpdate(PreparedStatement statement, Sala obj) throws SQLException {
        statement.setInt(1, obj.getEdificioId());
        statement.setString(2, obj.getNombre());
        statement.setInt(3, obj.getId());
    }
}
