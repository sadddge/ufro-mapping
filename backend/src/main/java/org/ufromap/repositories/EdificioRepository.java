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
    
    private SalaRepository salaRepository;

    public EdificioRepository() {
        this.salaRepository = new SalaRepository();
    }

    public EdificioRepository(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    

    public List<Edificio> getEdificios() {
        List<Edificio> edificios = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM edificio");
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return edificios;
    }

}
