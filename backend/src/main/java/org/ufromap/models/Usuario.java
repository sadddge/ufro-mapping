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
    @SerializedName("usuario_id")
    private int id;
    @Expose
    @SerializedName("nombre_usuario")
    private String nombre;
    @Expose
    @SerializedName("correo_usuario")
    private String correo;
    private String contrasenia;
    @Expose
    private Set<Asignatura> asignaturas;
}