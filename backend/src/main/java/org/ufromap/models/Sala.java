package org.ufromap.models;

import java.util.List;

public class Sala{
    private int id;
    private String nombre;
    private Edificio edificio;
    private List<Clase> clases;

    public Sala(int id, String nombre, Edificio edificio, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.edificio = edificio;
        this.clases = clases;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
}
