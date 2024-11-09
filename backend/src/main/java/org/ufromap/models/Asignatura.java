package org.ufromap.models;

import java.util.List;

/**
 * Clase que representa una Asignatura en el sistema. 
 * Una Asignatura tiene un nombre, código, descripción, valor en SCT (Sistema de Créditos Transferibles),
 * y una lista de clases asociadas.
 */
public class Asignatura {
    private int id;
    private String nombre;
    private String codigo;
    private String descripcion;
    private int sct;

    public Asignatura(int id, String nombre, String codigo, String descripcion, int sct) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.sct = sct;
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

    public void setNombre(String nombreAsignatura) {
        this.nombre = nombreAsignatura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSct() {
        return sct;
    }

    public void setSct(int sct) {
        this.sct = sct;
    }
}