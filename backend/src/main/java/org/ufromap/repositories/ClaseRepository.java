package org.ufromap.repositories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ufromap.config.DatabaseConnection;
import org.ufromap.models.Clase;

/**
 * Clase que implementa los métodos para gestionar la entidad Clase en la base de datos.
 * Provee funciones para obtener, agregar, actualizar y eliminar clases.
 */
public class ClaseRepository {

    private final Connection connection;

    public ClaseRepository(){
        this.connection = DatabaseConnection.getConnection();
    }

    public ClaseRepository(Connection connection){
        this.connection = connection;
    }

    /**
     * Obtiene todas las clases de la base de datos.
     * 
     * @return Una lista de objetos {@link Clase}, cada uno representando una fila en la tabla de clases.
     */
    public List<Clase> getClases(){
        List<Clase> clases = new ArrayList<>();
        String query= "SELECT * FROM clase";

        try(PreparedStatement stmt= connection.prepareStatement(query);
            ResultSet resultSet= stmt.executeQuery()){

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clases.add(new Clase(id,salaId,edificioId,asignaturaId,diaSemana,periodo,docenteNombre,modulo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return clases;
    }

    /**
     * Obtiene una clase específica de la base de datos por su ID.
     * 
     * @param id El ID de la clase que se quiere obtener.
     * @return Un objeto {@link Clase} si se encuentra, o {@code null} si no existe.
     */
    public Clase getClaseById(int id){

        Clase clase = null;
        String query= "SELECT * FROM clase WHERE id=?";

        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet resultSet= stmt.executeQuery();

            if(resultSet.next()){
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");

                clase = new Clase(id,salaId,edificioId,asignaturaId,diaSemana,periodo,docenteNombre,modulo);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

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
        String query= "SELECT * FROM clase WHERE sala_id=?";
    
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, sala_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");
    
                clase.add(new Clase(id,sala_id,edificioId,asignaturaId,diaSemana,periodo,docenteNombre,modulo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
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
        String query= "SELECT * FROM clase WHERE edificio_id=?";
    
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, edificio_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int salaId = resultSet.getInt("sala_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");
                
                clases.add(new Clase(id,salaId,edificio_id,asignaturaId,diaSemana,periodo,docenteNombre,modulo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
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
        String query= "SELECT * FROM clase WHERE asignatura_id=?";
    
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, asignatura_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int diaSemana = resultSet.getInt("dia_semana");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");
    
                clases.add(new Clase(id,salaId,edificioId,asignatura_id,diaSemana,periodo,docenteNombre,modulo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
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
    
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, diaSemana);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int salaId = resultSet.getInt("sala_id");
                int edificioId = resultSet.getInt("edificio_id");
                int asignaturaId = resultSet.getInt("asignatura_id");
                int periodo = resultSet.getInt("periodo");
                String docenteNombre = resultSet.getString("docente_nombre");
                int modulo = resultSet.getInt("modulo");
    
                clases.add(new Clase(id,salaId,edificioId,asignaturaId,diaSemana,periodo,docenteNombre,modulo));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return clases;
    }

    /**
     * Agrega una nueva clase a la base de datos.
     * 
     * @param clase El objeto {@link Clase} que se quiere agregar.
     * @return {@code true} si la clase fue agregada exitosamente, {@code false} en caso contrario.
     */
    public boolean addClase(Clase clase){
        boolean result = false;
        String query= "INSERT INTO clase(sala_id, edificio_id, asignatura_id, dia_semana, periodo, docente_nombre, modulo) VALUES(?,?,?,?,?,?,?)";

        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, clase.getSalaId());
            stmt.setInt(2, clase.getEdificioId());
            stmt.setInt(3, clase.getAsignaturaId());
            stmt.setInt(4, clase.getDiaSemana());
            stmt.setInt(5, clase.getPeriodo());
            stmt.setString(6, clase.getDocenteNombre());
            stmt.setInt(7, clase.getModulo());

            result = stmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

     /**
     * Actualiza una clase existente en la base de datos.
     * 
     * @param clase El objeto {@link Clase} con la información actualizada.
     * @return {@code true} si la clase fue actualizada exitosamente, {@code false} en caso contrario.
     */
    public boolean updateClase(Clase clase){
        boolean result = false;
        String query= "UPDATE clase SET sala_id=?, edificio_id=?, asignatura_id=?, dia_semana = ?, periodo = ?, docente_nombre=?, modulo=? WHERE id=?";

        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, clase.getSalaId());
            stmt.setInt(2, clase.getEdificioId());
            stmt.setInt(3, clase.getAsignaturaId());
            stmt.setInt(4, clase.getDiaSemana());
            stmt.setInt(5, clase.getPeriodo());
            stmt.setString(6, clase.getDocenteNombre());
            stmt.setInt(7, clase.getModulo());
            stmt.setInt(8, clase.getId());


            result = stmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
    * Borra una clase existente de la base de datos.
    * 
    * @param id El ID de la clase que se quiere borrar.
    * @return {@code true} si la clase fue eliminada exitosamente, {@code false} en caso contrario.
    */
    public boolean deleteClase(int id){
        boolean result = false;
        String query= "DELETE FROM clase WHERE id=?";

        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);

            result = stmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

}