package org.ufromap.models;

import java.util.List;

public class Asignatura {
    private String nombre;
    private String codigo;
    private String descripcion;
    private int SCT;
    private List<Clase> clases;


    public Asignatura(String nombre, String codigo, String descripcion, int SCT, List<Clase> clases) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.SCT = SCT;
        this.clases = clases;
    }


    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public int getSCT() { return SCT; }
    public List<Clase> getClases() { return clases; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setSCT(int SCT) { this.SCT = SCT; }
    public void setClases(List<Clase> clases) { this.clases = clases; }
}