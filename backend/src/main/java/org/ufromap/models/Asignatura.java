package org.ufromap.models;

import java.util.List;

/**
 * Clase que representa una Asignatura en el sistema. 
 * Una Asignatura tiene un nombre, código, descripción, valor en SCT (Sistema de Créditos Transferibles),
 * y una lista de clases asociadas.
 */
public class Asignatura {
    private String nombre;
    private String codigo;
    private String descripcion;
    private int SCT;
    private List<Clase> clases;

    /**
     * Constructor que inicializa una Asignatura con sus atributos.
     * @param nombre el nombre de la asignatura.
     * @param codigo el código único de la asignatura.
     * @param descripcion una breve descripción de la asignatura.
     * @param SCT los créditos SCT de la asignatura.
     * @param clases la lista de clases asociadas a la asignatura.
     */
    public Asignatura(String nombre, String codigo, String descripcion, int SCT, List<Clase> clases) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.SCT = SCT;
        this.clases = clases;
    }

    /**
     *
     * @return el valor del atributo nombre.
     */
    public String getNombre() { return nombre; }
    /**
     *
     * @return el valor del atributo codigo.
     */
    public String getCodigo() { return codigo; }
    /**
     *
     * @return el valor del atributo descripcion.
     */
    public String getDescripcion() { return descripcion; }
    /**
     *
     * @return el valor del atributo SCT.
     */
    public int getSCT() { return SCT; }
    /**
     *
     * @return el valor del atributo clases el cual es una lista de clases.
     */
    public List<Clase> getClases() { return clases; }

    /**
     * Establece el valor del atributo nombre.
     * @param nombre el nuevo valor del atributo.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Establece el valor del atributo codigo.
     * @param codigo el nuevo valor del atributo.
     */
    public void setCodigo(String codigo) { this.codigo = codigo; }
    /**
     * Establece el valor del atributo descripcion.
     * @param descripcion el nuevo valor del atributo.
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    /**
     * Establece el valor del atributo SCT.
     * @param SCT el nuevo valor del atributo.
     */
    public void setSCT(int SCT) { this.SCT = SCT; }
    /**
     * Establece el valor del atributo clases.
     * @param clases el nuevo valor del atributo.
     */
    public void setClases(List<Clase> clases) { this.clases = clases; }
}