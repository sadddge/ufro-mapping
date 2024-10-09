package org.ufromap.models;

import java.util.List;

import org.ufromap.Clase;

public class Sala{
    private long id;
    private String nombre;
    private List<Clase> clases;

    public Sala(long id, String nombre, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.clases = clases;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
}
