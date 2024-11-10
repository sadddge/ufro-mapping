package org.ufromap.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    @Expose
    @SerializedName("usuario_id")
    private int id;
    @Expose
    @SerializedName("nombre_usuario")
    private String nombre;
    private String contrasenia;
    @Expose
    @SerializedName("correo_usuario")
    private String correo;
    @Expose
    private Set<Asignatura> asignaturas;
}