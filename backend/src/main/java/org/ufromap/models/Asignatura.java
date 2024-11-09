package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Clase que representa una Asignatura en el sistema.
 * Una Asignatura tiene un nombre, código, descripción, valor en SCT (Sistema de Créditos Transferibles),
 * y una lista de clases asociadas.
 */
@Getter
@Setter
public class Asignatura {
    private int id;
    @SerializedName("nombre_asignatura")
    private String nombre;
    private String codigo;
    private String descripcion;
    private int sct;
    private List<Clase> clases;

    /**
     * Constructor que inicializa una Asignatura con sus atributos.
     *
     * @param id          la id único de la asignatura.
     * @param nombre      el nombre de la asignatura.
     * @param codigo      el código único de la asignatura.
     * @param descripcion una breve descripción de la asignatura.
     * @param sct         los créditos SCT de la asignatura.
     * @param clases      la lista de clases asociadas a la asignatura.
     */
    public Asignatura(int id, String nombre, String codigo, String descripcion, int sct, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.sct = sct;
        this.clases = clases;
    }
}