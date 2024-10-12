package org.ufromap.repositories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;

/**
 * Clase que implementa los métodos para gestionar la entidad Clase en la base de datos.
 * Provee funciones para obtener, agregar, actualizar y eliminar clases.
 */
public class ClaseRepository {
    
    /**
     * Obtiene todas las clases de la base de datos.
     * 
     * @return Una lista de objetos {@link Clase}, cada uno representando una fila en la tabla de clases.
     */
    public List<Clase> getClases(){
        List<Clase> clases = new ArrayList<>();
        String query= "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query);
            ResultSet resultSet= stmt.executeQuery()){
            while(resultSet.next()){
                int id = resultSet.getInt("clase_id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clases.add(new Clase(id, salaId, edificioId, asignaturaId, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clases;
    }

    /**
     * Obtiene una clase específica de la base de datos por su ID.
     * 
     * @param id El ID de la clase que se quiere obtener.
     * @return Un objeto {@link Clase} si se encuentra, o {@code null} si no existe.
     */
    public Optional<Clase> getClaseById(int id){

        Optional<Clase> clase = Optional.empty();
        String query= "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE clase_id=?";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet resultSet= stmt.executeQuery();

            if(resultSet.next()){
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clase = Optional.of(new Clase(id, salaId, edificioId, asignaturaId, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clase;
    }

    /**
     * Obtiene todas las clases de una sala específica.
     * 
     * @param sala_id El ID de la sala.
     * @return Una lista de objetos {@link Clase} que ocurren en la sala indicada.
     */
    public List<Clase> getClasesBySalaId(int sala_id){
        List<Clase> clase = new ArrayList<>();
        String query= "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE sala_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, sala_id);
            ResultSet resultSet= stmt.executeQuery();

            if(resultSet.next()){
                int id = resultSet.getInt("clase_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clase.add(new Clase(id, sala_id, edificioId, asignaturaId, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clase;
    }

    /**
     * Obtiene todas las clases que se dictan en un edificio específico.
     * 
     * @param edificio_id El ID del edificio.
     * @return Una lista de objetos {@link Clase} que se dictan en el edificio indicado.
     */
    public List<Clase> getClasesByEdificioId(int edificio_id){

        List<Clase> clases = new ArrayList<>();
        String query= "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE edificio_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, edificio_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("clase_id");
                int salaId = resultSet.getInt("sala_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clases.add(new Clase(id, salaId, edificio_id, asignaturaId, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clases;
    }

     /**
     * Obtiene todas las clases de una asignatura específica.
     * 
     * @param asignatura_id El ID de la asignatura.
     * @return Una lista de objetos {@link Clase} correspondientes a la asignatura indicada.
     */
    public List<Clase> getClasesByAsignaturaId(int asignatura_id){
        List<Clase> clases = new ArrayList<>();
        String query= "SELECT clase_id, sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo FROM clase WHERE asignatura_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, asignatura_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("clase_id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clases.add(new Clase(id, salaId, edificioId, asignatura_id, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clases;
    }

    /**
     * Obtiene todas las clases que se dictan en un día específico.
     * 
     * @param diaSemana El día de la semana (por ejemplo, "Lunes").
     * @return Una lista de objetos {@link Clase} que ocurren en el día indicado.
     */
    public List<Clase> getClasesByDiaSemana(int diaSemana){
        List<Clase> clases = new ArrayList<>();
        String query= "SELECT * FROM clase WHERE dia_semana=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, diaSemana);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("clase_id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int periodo = resultSet.getInt("periodo_clase");
                String docente = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clases.add(new Clase(id, salaId, edificioId, asignaturaId, diaSemana, periodo, docente, modulo));
            }
        }catch(SQLException e){ }
        return clases;
    }

    /**
     * Agrega una nueva clase a la base de datos.
     * 
     * @param clase El objeto {@link Clase} que se quiere agregar.
     */
    public void addClase(Clase clase){
        String query= "INSERT INTO clase(sala_id, edificio_id, asignatura_id, dia_semana, periodo_clase, docente_nombre, modulo) VALUES(?,?,?,?,?,?,?)";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, clase.getSalaId());
            stmt.setInt(2, clase.getEdificioId());
            stmt.setInt(3, clase.getAsignaturaId());
            stmt.setInt(4, clase.getDiaSemana());
            stmt.setInt(5, clase.getPeriodo());
            stmt.setString(6, clase.getDocente());
            stmt.setInt(7, clase.getModulo());
            stmt.executeUpdate();
        }catch(SQLException e){ }
    }

     /**
     * Actualiza una clase existente en la base de datos.
     * 
     * @param clase El objeto {@link Clase} con la información actualizada.
     */
    public void updateClase(Clase clase){
        String query= "UPDATE clase SET sala_id=?, edificio_id=?, asignatura_id=?, dia_semana=?, periodo_clase=?, docente_nombre=?, modulo=? WHERE clase_id=?";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, clase.getSalaId());
            stmt.setInt(2, clase.getEdificioId());
            stmt.setInt(3, clase.getAsignaturaId());
            stmt.setInt(4, clase.getDiaSemana());
            stmt.setInt(5, clase.getPeriodo());
            stmt.setString(6, clase.getDocente());
            stmt.setInt(7, clase.getModulo());
            stmt.setInt(8, clase.getId());
            stmt.executeUpdate();
        }catch(SQLException e){ }
    }

    /**
    * Borra una clase existente de la base de datos.
    * 
    * @param id El ID de la clase que se quiere borrar.
    */
    public void deleteClase(int id){
        String query= "DELETE FROM clase WHERE clase_id=?";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch(SQLException e){ }
    }

}