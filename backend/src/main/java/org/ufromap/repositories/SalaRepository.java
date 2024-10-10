package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.Clase;
import org.ufromap.Edificio;
import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Sala;

public class SalaRepository {

    private ClaseRepository claseRepository;
    private EdificioRepository edificioRepository;

    public SalaRepository() {
        this.claseRepository = new ClaseRepository();
        this.edificioRepository = new EdificioRepository();
    }

    public SalaRepository(ClaseRepository claseRepository, EdificioRepository edificioRepository) {
        this.claseRepository = claseRepository;
        this.edificioRepository = edificioRepository;
    }
    
    public List<Sala> getSalas() throws SQLException {
        List<Sala> salas = new ArrayList<>();
        String query = "SELECT * FROM sala";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("sala_id");
                String nombre = resultSet.getString("sala");
                Edificio edificio = edificioRepository.getEdificioById(resultSet.getInt("edificio_id"));
                List<Clase> clases = claseRepository.getClasesBySalaId(id);
                Sala sala = new Sala(id, nombre, edificio, clases);
                salas.add(sala);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return salas;
    }

    public Sala getSalaById(int id) throws SQLException {
        Sala sala = null;
        String query = "SELECT * FROM salas WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("sala");
                    Edificio edificio = edificioRepository.getEdificioById(resultSet.getInt("edificio_id"));
                    List<Clase> clases = claseRepository.getClasesBySalaId(id);
                    sala = new Sala(id, nombre, edificio, clases);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sala;
    }

    public boolean addSala(Sala sala) throws SQLException {
        String query = "INSERT INTO salas (edificio_id, sala) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, sala.getEdificio().getId());
            preparedStatement.setString(2, sala.getNombre());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSala(Sala sala) throws SQLException {
        String query = "UPDATE sala SET nombre = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, sala.getNombre());
            preparedStatement.setLong(2, sala.getEdificio().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSala(long id) throws SQLException {
        String query = "DELETE FROM sala WHERE sala_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
