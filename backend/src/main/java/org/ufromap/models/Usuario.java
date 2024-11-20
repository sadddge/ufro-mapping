package org.ufromap.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Expose
    private int id;
    @Expose
    private String nombre;
    @Expose
    private String correo;
    private String contrasenia;
    @Expose
    private Set<Asignatura> asignaturas;
}