package org.ufromap.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Representa un usuario con atributos de nombre, contraseña, correo y asignaturas.
 * Proporciona métodos para gestionar la sesión y actualizar información del usuario.
 */
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

    private Set<Asignatura> asignaturas;

    /**
     * Constructor por defecto de Usuario no registrado.
     */
    public Usuario() {

    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Set<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
