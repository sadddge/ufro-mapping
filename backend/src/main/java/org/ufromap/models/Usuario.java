package org.ufromap.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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


    /**
     * Constructor que inicializa un usuario con un nombre, contraseña, correo y un conjunto de asignaturas.
     *
     * @param nombre el nombre del usuario
     * @param contrasenia la contraseña del usuario
     * @param correo el correo del usuario
     * @param asignaturas el conjunto de asignaturas del usuario
     */
    public Usuario(int id, String nombre, String correo, String contrasenia, Set<Asignatura> asignaturas) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.asignaturas = asignaturas;
    }
}
