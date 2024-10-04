package org.ufromap;

import java.util.List;

public class Sala implements InfoMostrable, ClaseMostrable {
    private long id;
    private String nombre;
    private List<Clase> clases;

    public Sala(long id, String nombre, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.clases = clases;
    }

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}
