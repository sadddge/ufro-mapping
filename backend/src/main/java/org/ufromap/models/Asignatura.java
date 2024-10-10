package org.ufromap.models;

import java.util.List;

import org.ufromap.Clase;

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


    public void mostrarClases() {
        for (Clase clase : clases) {
            clase.mostrarInformacion();
        }
    }


    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Código: " + codigo);
        System.out.println("Descripción: " + descripcion);
        System.out.println("SCT: " + SCT);
        mostrarClases();
    }
}