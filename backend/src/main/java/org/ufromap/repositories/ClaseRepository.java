package org.ufromap.repositories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ufromap.Clase;


public class ClaseRepository {

    private List<Clase> clases;

    public ClaseRepository(List<Clase> clases) {
        this.clases = clases;
    }

    public List<Clase> getClases() {
        return clases;
    }


    public void getAllClases(){
        String query= "SELECT * FROM clase";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query);
            ResultSet resultSet= stmt.executeQuery()){

            while(resultSet.next()){
                String docente = resultSet.getString("docente_nombre");
                String diaSemana = resultSet.getString("dia_semana");
                String horaInicio = resultSet.getString("hora_inicio");
                String horaFin = resultSet.getString("hora_fin");
                int modulo = resultSet.getInt("modulo");
                Sala sala = resultSet.getSala(sala_id);

                Clase clase = new Clase(docente, diaSemana, horaInicio, horaFin, modulo, sala);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public Clase getClasesById(int id){

        Clase clase = null;
        String query= "SELECT * FROM clase WHERE clase_id=?";

        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet resultSet= stmt.executeQuery();

            if(resultSet.next()){
                String docente = resultSet.getString("docente_nombre");
                String diaSemana = resultSet.getString("dia_semana");
                String horaInicio = resultSet.getString("hora_inicio");
                String horaFin = resultSet.getString("hora_fin");
                int modulo = resultSet.getInt("modulo");
                Sala sala = resultSet.getSala(sala_id);

                clase = new Clase(docente, diaSemana, horaInicio, horaFin, modulo, sala);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return clase;

    }

    public Clase getClasesBySalaId(int sala_id){
            
        Clase clase = null;
        String query= "SELECT * FROM clase WHERE sala_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, sala_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                String docente = resultSet.getString("docente_nombre");
                String diaSemana = resultSet.getString("dia_semana");
                String horaInicio = resultSet.getString("hora_inicio");
                String horaFin = resultSet.getString("hora_fin");
                int modulo = resultSet.getInt("modulo");
                Sala sala = resultSet.getSala(sala_id);
    
                clase = new Clase(docente, diaSemana, horaInicio, horaFin, modulo, sala);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return clase;
    }

    public Clase getClasesByEdificioId(int edificio_id){

        Clase clase = null;
        String query= "SELECT * FROM clase WHERE edificio_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, edificio_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                String docente = resultSet.getString("docente_nombre");
                String diaSemana = resultSet.getString("dia_semana");
                String horaInicio = resultSet.getString("hora_inicio");
                String horaFin = resultSet.getString("hora_fin");
                int modulo = resultSet.getInt("modulo");
                Sala sala = resultSet.getSala(sala_id);
    
                clase = new Clase(docente, diaSemana, horaInicio, horaFin, modulo, sala);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return clase;


    }

    public Clase getClasesByAsignaturaId(int asignatura_id){

        Clase clase = null;
        String query= "SELECT * FROM clase WHERE asignatura_id=?";
    
        try(Connection connection= DatabaseConnection.getConnection();
            PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, asignatura_id);
            ResultSet resultSet= stmt.executeQuery();
    
            if(resultSet.next()){
                String docente = resultSet.getString("docente_nombre");
                String diaSemana = resultSet.getString("dia_semana");
                String horaInicio = resultSet.getString("hora_inicio");
                String horaFin = resultSet.getString("hora_fin");
                int modulo = resultSet.getInt("modulo");
                Sala sala = resultSet.getSala(sala_id);
    
                clase = new Clase(docente, diaSemana, horaInicio, horaFin, modulo, sala);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return clase;
    }


}