package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;
import org.ufromap.models.Sala;

/**
 * Repositorio que gestiona las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad {@link Sala}.
 * Se conecta a la base de datos y ejecuta consultas SQL para manipular los datos de la tabla "sala".
 */
public class SalaRepository extends BaseRepository<Sala> {

    private final ClaseRepository claseRepository;

    /**
     * Constructor por defecto que inicializa el repositorio con instancias de los servicios
     * de {@link ClaseRepository}.
     */
    public SalaRepository() {
        this.claseRepository = new ClaseRepository();
    }


    public List<Sala> findByEdificioId(int edificioId) {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT id, edificio_id, nombre_sala FROM sala WHERE edificio_id = ?";
        Connection connection = DatabaseConnection.getConnection();
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
        try {
            int id = resultSet.getInt("id");
            int edificioId = resultSet.getInt("edificio_id");
            String nombre = resultSet.getString("nombre_sala");
            List<Clase> clases = claseRepository.findBySalaId(id);
            return new Sala(id, edificioId, nombre, clases);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
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
