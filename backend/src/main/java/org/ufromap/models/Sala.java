package org.ufromap;

import java.util.List;

public class Sala implements InfoMostrable, ClaseMostrable {
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

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}
