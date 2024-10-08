package org.ufromap.repositories;

import org.ufromap.Usuario;
import org.ufromap.models.AsignaturaModel;

public class AsignaturaRepository {

    public void registrarAsignatura(Usuario usuario){
        
    }

    public AsignaturaModel getAsignaturaByCodigo(Usuario usuario, String codigo) {


    String queryAsignatura = "SELECT * FROM asignaturas WHERE codigo = ?";
    String queryClases = "SELECT * FROM clases WHERE codigo_asignatura = ?";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_datos", "usuario", "contrasena");
         PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura);
         PreparedStatement stmtClases = connection.prepareStatement(queryClases)) {
        stmtAsignatura.setString(1, codigo);
        try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {
            if (resultSetAsignatura.next()) {
                String nombre = resultSetAsignatura.getString("nombre");
                String descripcion = resultSetAsignatura.getString("descripcion");
                int sct = resultSetAsignatura.getInt("sct");
                System.out.println("Asignatura encontrada: " + nombre + ", Descripción: " + descripcion + ", SCT: " + sct);

                stmtClases.setString(1, codigo);
                try (ResultSet resultSetClases = stmtClases.executeQuery()) {
                    System.out.println("Clases asociadas:");

                    while (resultSetClases.next()) {
                        String docente = resultSetClases.getString("docente");
                        String diaSemana = resultSetClases.getString("dia_semana");
                        String horaInicio = resultSetClases.getString("hora_inicio");
                        String horaFin = resultSetClases.getString("hora_fin");
                        String modulo = resultSetClases.getString("modulo");

                        System.out.println("Docente: " + docente + ", Día: " + diaSemana + ", Horario: " + horaInicio + " - " + horaFin + ", Módulo: " + modulo);
                    }
                }
            } else {
                System.out.println("No se encontró una asignatura con el código: " + codigo);
            }
        }

        } catch (SQLException e) {
         e.printStackTrace();
        }
    }


    public AsignaturaModel getAsignaturaByNombre(Usuario usuario, String nombre) {
        String queryAsignatura = "SELECT * FROM asignaturas WHERE nombre = ?";
        String queryClases = "SELECT * FROM clases WHERE codigo_asignatura = ?";
    
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_datos", "usuario", "contrasena");
             PreparedStatement stmtAsignatura = connection.prepareStatement(queryAsignatura);
             PreparedStatement stmtClases = connection.prepareStatement(queryClases)) {
            stmtAsignatura.setString(1, nombre);
            try (ResultSet resultSetAsignatura = stmtAsignatura.executeQuery()) {
                if (resultSetAsignatura.next()) {
                    String codigo = resultSetAsignatura.getString("codigo");
                    String descripcion = resultSetAsignatura.getString("descripcion");
                    int sct = resultSetAsignatura.getInt("sct");
                    System.out.println("Asignatura encontrada: " + nombre + ", Código: " + codigo + ", Descripción: " + descripcion + ", SCT: " + sct);
    
                    stmtClases.setString(1, codigo);
                    try (ResultSet resultSetClases = stmtClases.executeQuery()) {
                        System.out.println("Clases asociadas:");
    
                        while (resultSetClases.next()) {
                            String docente = resultSetClases.getString("docente");
                            String diaSemana = resultSetClases.getString("dia_semana");
                            String horaInicio = resultSetClases.getString("hora_inicio");
                            String horaFin = resultSetClases.getString("hora_fin");
                            String modulo = resultSetClases.getString("modulo");
    
                            System.out.println("Docente: " + docente + ", Día: " + diaSemana + ", Horario: " + horaInicio + " - " + horaFin + ", Módulo: " + modulo);
                        }
                    }
                } else {
                    System.out.println("No se encontró una asignatura con el nombre: " + nombre);
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}