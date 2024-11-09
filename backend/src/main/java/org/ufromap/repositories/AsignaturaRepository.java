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



/**
 * Clase repositorio para manejar las operaciones relacionadas con la entidad "Asignatura" en la base de datos.
 * Proporciona métodos para recuperar, agregar, actualizar y eliminar registros de "Asignatura",
 * así como para obtener las "Clase" relacionadas.
 */
public class AsignaturaRepository {

    private final ClaseRepository clasesRepository;

    /**
     * Constructor que inicializa el repositorio con una instancia de ClaseRepository.
     * @param clasesRepository el repositorio para manejar las operaciones relacionadas con Clase.
     */
    public AsignaturaRepository(ClaseRepository clasesRepository) {
        this.clasesRepository = clasesRepository;
    }

    /**
     * Constructor por defecto que inicializa el repositorio con una nueva instancia de ClaseRepository.
     */
    public AsignaturaRepository() {
        this.clasesRepository = new ClaseRepository();
    }

    /**
     * Recupera todas las asignaturas de la base de datos.
     * @return una lista de objetos Asignatura.
     */
    public List<Asignatura> getAsignaturas() {
        List<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT * FROM asignatura";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre_asignatura");
                String codigo = resultSet.getString("codigo");
                String descripcion = resultSet.getString("descripcion");
                int sct = resultSet.getInt("sct");


                Asignatura asignatura = new Asignatura(id, nombre, codigo, descripcion, sct);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {

        }

        return asignaturas;
    }

    /**
     * Recupera una asignatura de la base de datos basándose en su ID.
     * @param id el ID de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de SQL durante el proceso.
     */
    public Asignatura getAsignaturaById(int id) throws SQLException {
        
        Asignatura asignatura = null;
        String queryAsignatura = "SELECT * FROM asignatura WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setInt(1, id);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    String nombre = resultSetAsignatura.getString("nombre_asignatura");
                    String codigo = resultSetAsignatura.getString("codigo");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");


                    asignatura = new Asignatura(id, nombre, codigo, descripcion, sct);

                    
                    
                } 
                
            } catch (SQLException e) {

            }
        }

        return asignatura;
    }

    /**
     * Recupera una asignatura de la base de datos basándose en su código.
     * @param codigo el código único de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
    public Asignatura getAsignaturaByCodigo(String codigo) {
        Asignatura asignatura = null;
        String queryAsignatura = "SELECT * FROM asignatura WHERE codigo = ?";
 
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setString(1, codigo);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    int id = resultSetAsignatura.getInt("id");
                    String nombre = resultSetAsignatura.getString("nombre_asignatura");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");


                    asignatura = new Asignatura(id, nombre, codigo, descripcion, sct);

                    
                    
                } 
                
            } catch (SQLException e) {
            }

        } catch (SQLException ex) {

        }
        

        return asignatura;

    }

    /**
     * Recupera una asignatura de la base de datos basándose en su nombre.
     * @param nombre el nombre de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
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
                    asignatura = new Asignatura(id, nombre, codigo, descripcion, sct);
                }   
            } catch (SQLException e) {

            }

        } catch (SQLException ex) {

        }
        return asignatura;
    }


    public void addAsignatura(Asignatura asignatura) {
        String query = "INSERT INTO asignatura (nombre_asignatura, codigo, descripcion,sct) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, asignatura.getNombre());
            stmt.setString(2, asignatura.getCodigo());
            stmt.setString(3, asignatura.getDescripcion());
            stmt.setInt(4, asignatura.getSct());
            stmt.executeUpdate();


        } catch (SQLException e) {

        }
    }


    public void updateAsignatura(Asignatura asignatura) {
        String query = "UPDATE asignatura SET nombre_asignatura = ?, codigo = ?, descripcion = ?, sct = ? WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, asignatura.getNombre());
            stmt.setString(2, asignatura.getCodigo());
            stmt.setString(3, asignatura.getDescripcion());
            stmt.setInt(4, asignatura.getSct());
            stmt.setInt(5, asignatura.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    /**
     * Elimina una asignatura de la base de datos basándose en su ID.
     * @param asignatura_id el ID de la asignatura a eliminar.
     */
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