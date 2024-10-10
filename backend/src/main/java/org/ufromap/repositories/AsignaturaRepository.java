package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Clase;

public class AsignaturaRepository {

    private final ClaseRepository clasesRepository;

    public AsignaturaRepository(ClaseRepository clasesRepository) {
        this.clasesRepository = clasesRepository;
    }

    public AsignaturaRepository() {
        this.clasesRepository = new ClaseRepository();
    }

    public List<Asignatura> getAsignaturas() {
        List<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT * FROM asignatura";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("asignatura_id");
                String nombre = resultSet.getString("nombre");
                String codigo = resultSet.getString("codigo");
                String descripcion = resultSet.getString("descripcion");
                int sct = resultSet.getInt("sct");
                List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);

                Asignatura asignatura = new Asignatura(nombre, codigo, descripcion, sct, clases);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {

        }

        return asignaturas;
    }

    public Asignatura getAsignaturaById(int id) throws SQLException {
        
        Asignatura asignatura = null;
        String queryAsignatura = "SELECT * FROM asignatura WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setInt(1, id);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    String nombre = resultSetAsignatura.getString("nombre");
                    String codigo = resultSetAsignatura.getString("codigo");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);

                    asignatura = new Asignatura(nombre, codigo, descripcion, sct, clases);

                    
                    
                } 
                
            } catch (SQLException e) {

            }
        }

        return asignatura;
    }

    public Asignatura getAsignaturaByCodigo(String codigo) {
        Asignatura asignatura = null;
        String queryAsignatura = "SELECT * FROM asignatura WHERE codigo = ?";
 
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setString(1, codigo);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    int id = resultSetAsignatura.getInt("id");
                    String nombre = resultSetAsignatura.getString("nombre");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);

                    asignatura = new Asignatura(nombre, codigo, descripcion, sct, clases);

                    
                    
                } 
                
            } catch (SQLException e) {
            }

        } catch (SQLException ex) {

        }
        

        return asignatura;

    }


    public Asignatura getAsignaturaByNombre(String nombre) {
            
        Asignatura asignatura = null;
        String queryAsignatura = "SELECT * FROM asignatura WHERE asignatura = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){

            stmtAsignatura.setString(1, nombre);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {
                if (resultSetAsignatura.next()) {
                    int id = resultSetAsignatura.getInt("id");
                    String codigo = resultSetAsignatura.getString("codigo");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);
                    asignatura = new Asignatura(nombre, codigo, descripcion, sct, clases); 
                }   
            } catch (SQLException e) {

            }

        } catch (SQLException ex) {

        }
        return asignatura;
    }

    

    public void addAsignatura(String asignatura, String codigo, String descripcion) {
        String query = "INSERT INTO asignatura (asignatura, codigo, descripcion) VALUES (?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, asignatura);
            stmt.setString(2, codigo);
            stmt.setString(3, descripcion);
            stmt.executeUpdate();


        } catch (SQLException e) {

        }
    }

    public void updateAsignatura(String asignatura, String codigo, String descripcion, int asignatura_id) {
        String query = "UPDATE asignatura SET asignatura = ?, codigo = ?, descripcion = ? WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, asignatura);
            stmt.setString(2, codigo);
            stmt.setString(3, descripcion);
            stmt.setInt(4, asignatura_id);

            stmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void deleteAsignatura(int asignatura_id) {
        String query = "DELETE FROM asignatura WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, asignatura_id);

            stmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    
    
}