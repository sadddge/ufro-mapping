package org.ufromap.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.cj.log.Log;
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
        String query = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("asignatura_id");
                String nombre = resultSet.getString("nombre_asignatura");
                String codigo = resultSet.getString("codigo_asignatura");
                String descripcion = resultSet.getString("descripcion_asignatura");
                int sct = resultSet.getInt("sct_asignatura");
                List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);

                Asignatura asignatura = new Asignatura(id, nombre, codigo, descripcion, sct, clases);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) { }
        return asignaturas;
    }

    /**
     * Recupera una asignatura de la base de datos basándose en su ID.
     * @param id el ID de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de SQL durante el proceso.
     */
    public Optional<Asignatura> getAsignaturaById(int id) throws SQLException {
        
        Optional<Asignatura> asignatura = Optional.empty();
        String queryAsignatura = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setInt(1, id);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    String nombre = resultSetAsignatura.getString("nombre_asignatura");
                    String codigo = resultSetAsignatura.getString("codigo_asignatura");
                    String descripcion = resultSetAsignatura.getString("descripcion_asignatura");
                    int sct = resultSetAsignatura.getInt("sct_asignatura");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);
                    asignatura = Optional.of(new Asignatura(id, nombre, codigo, descripcion, sct, clases));
                } 
                
            } catch (SQLException e) { }
        } catch (SQLException ex) { }
        return asignatura;
    }

    /**
     * Recupera una asignatura de la base de datos basándose en su código.
     * @param codigo el código único de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
    public Optional<Asignatura> getAsignaturaByCodigo(String codigo) {
        Optional<Asignatura> asignatura = Optional.empty();
        String queryAsignatura = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura WHERE codigo_asignatura = ?";
 
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){
            stmtAsignatura.setString(1, codigo);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {

                if (resultSetAsignatura.next()) {
                    int id = resultSetAsignatura.getInt("asignatura_id");
                    String nombre = resultSetAsignatura.getString("nombre_asignatura");
                    String descripcion = resultSetAsignatura.getString("descripcion_asignatura");
                    int sct = resultSetAsignatura.getInt("sct_asignatura");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);

                    asignatura = Optional.of(new Asignatura(id, nombre, codigo, descripcion, sct, clases));
                } 
                
            } catch (SQLException e) {}
        } catch (SQLException ex) {}
        return asignatura;
    }

    /**
     * Recupera una asignatura de la base de datos basándose en su nombre.
     * @param nombre el nombre de la asignatura a recuperar.
     * @return el objeto Asignatura si se encuentra, null en caso contrario.
     */
    public Optional<Asignatura> getAsignaturaByNombre(String nombre) {

        Optional<Asignatura> asignatura = Optional.empty();
        String queryAsignatura = "SELECT asignatura_id, nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura FROM asignatura WHERE nombre_asignatura = ?";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura)){

            stmtAsignatura.setString(1, nombre);

            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {
                if (resultSetAsignatura.next()) {
                    int id = resultSetAsignatura.getInt("asignatura_id");
                    String codigo = resultSetAsignatura.getString("codigo_asignatura");
                    String descripcion = resultSetAsignatura.getString("descripcion_asignatura");
                    int sct = resultSetAsignatura.getInt("sct_asignatura");
                    List<Clase> clases = clasesRepository.getClasesByAsignaturaId(id);
                    asignatura = Optional.of(new Asignatura(id, nombre, codigo, descripcion, sct, clases));
                }
            } catch (SQLException e) { }
        } catch (SQLException ex) { }
        return asignatura;
    }

    /**
     * Agrega una nueva asignatura a la base de datos.
     * @param nombre el nombre de la asignatura.
     * @param codigo el código de la asignatura.
     * @param descripcion una breve descripción de la asignatura.
     * @param sct los créditos SCT de la asignatura.
     */
    public void addAsignatura(int nombre, String codigo, String descripcion, int sct) {
        String query = "INSERT INTO asignatura (nombre_asignatura, codigo_asignatura, descripcion_asignatura, sct_asignatura) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, nombre);
            stmt.setString(2, codigo);
            stmt.setString(3, descripcion);
            stmt.setInt(4, sct);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }

    /**
     * Actualiza una asignatura existente en la base de datos.
     * @param id el ID de la asignatura a actualizar.
     * @param nombre el nuevo nombre de la asignatura.
     * @param codigo el nuevo código de la asignatura.
     * @param descripcion la nueva descripción de la asignatura.
     * @param sct los nuevos créditos SCT de la asignatura.
     */
    public void updateAsignatura(int id, String nombre, String codigo, String descripcion, int sct) {
        String query = "UPDATE asignatura SET nombre_asignatura = ?, codigo_asignatura = ?, descripcion_asignatura = ?, sct_asignatura = ? WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, codigo);
            stmt.setString(3, descripcion);
            stmt.setInt(4, sct);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }

    /**
     * Elimina una asignatura de la base de datos basándose en su ID.
     * @param id el ID de la asignatura a eliminar.
     */
    public void deleteAsignatura(int id) {
        String query = "DELETE FROM asignatura WHERE asignatura_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { }
    }
}