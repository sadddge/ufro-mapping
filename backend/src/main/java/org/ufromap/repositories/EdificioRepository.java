package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Edificio;
import org.ufromap.models.Sala;


public class EdificioRepository {
    
    private final SalaRepository salaRepository;

    public EdificioRepository() {
        this.salaRepository = new SalaRepository();
    }

    public EdificioRepository(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Edificio> getEdificios() {
        List<Edificio> edificios = new ArrayList<>();
        String query = "SELECT * FROM edificio";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()){
            
            while (resultSet.next()) {
                int id = resultSet.getInt("edificio_id");
                String nombre = resultSet.getString("edificio");
                String alias = resultSet.getString("alias");
                String tipo = resultSet.getString("tipo");
                float latitud = resultSet.getFloat("latitud");
                float longitud = resultSet.getFloat("longitud");
                List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                edificios.add(new Edificio(id, nombre, alias, latitud, longitud, tipo, salas));
            }

        } catch (SQLException e) {

        }
        return edificios;
    }

    public Edificio getEdificioById(int id) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE edificio_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    String nombre = resultSet.getString("edificio");
                    String alias = resultSet.getString("alias");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, latitud, longitud, tipo, salas);
                }
            }
            connection.close();
        } catch (SQLException e) {

        }
        return edificio;
    }

    public Edificio getEdificioByNombre(String nombre) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE edificio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ){
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String alias = resultSet.getString("alias");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, latitud, longitud, tipo, salas);
                }
            }
            connection.close();
        } catch (SQLException e) {

        }
        return edificio;
    }

    public Edificio getEdificioByAlias(String alias) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE alias = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ){
            statement.setString(1, alias);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String nombre = resultSet.getString("edificio");
                    String tipo = resultSet.getString("tipo");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, latitud, longitud, tipo, salas);
                }
            }
            connection.close();
        } catch (SQLException e) {

        }
        return edificio;
    }

    public Edificio getEdificioByTipo(String tipo) {
        Edificio edificio = null;
        String query = "SELECT * FROM edificio WHERE tipo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ){
            statement.setString(1, tipo);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    int id = resultSet.getInt("edificio_id");
                    String nombre = resultSet.getString("edificio");
                    String alias = resultSet.getString("alias");
                    float latitud = resultSet.getFloat("latitud");
                    float longitud = resultSet.getFloat("longitud");
                    List<Sala> salas = salaRepository.getSalasByEdificioId(id);
                    edificio = new Edificio(id, nombre, alias, latitud, longitud, tipo, salas);
                }
            }
            connection.close();
        } catch (SQLException e) {

        }
        return edificio;
    }


    public boolean addEdificio(Edificio edificio) {
        String query = "INSERT INTO edificio (edificio, alias, latitud, longitud, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setFloat(3, edificio.getLatitud());
            statement.setFloat(4, edificio.getLongitud());
            statement.setString(5, edificio.getTipo());
            statement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateEdificio(Edificio edificio) {
        String query = "UPDATE edificio SET edificio = ?, alias = ?, latitud = ?, longitud = ?, tipo = ? WHERE edificio_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, edificio.getNombre());
            statement.setString(2, edificio.getAlias());
            statement.setFloat(3, edificio.getLatitud());
            statement.setFloat(4, edificio.getLongitud());
            statement.setString(5, edificio.getTipo());
            statement.setInt(6, edificio.getId());
            statement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteEdificio(int id) {
        String query = "DELETE FROM edificio WHERE edificio_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
