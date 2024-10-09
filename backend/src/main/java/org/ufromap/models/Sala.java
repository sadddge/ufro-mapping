package org.ufromap.models;

import java.util.List;

import org.ufromap.Clase;

public class Sala{
    private final long id;
    private final String nombre;
    private final List<Clase> clases;

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

}
