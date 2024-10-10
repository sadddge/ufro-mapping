package org.ufromap.models;

import java.util.List;

public class Edificio{
    private int id;
    private String nombre;
    private String alias;
    private float latitud;
    private float longitud;
    private String tipo;
    private List<Sala> salas;

    public Edificio(int id, String nombre, String alias, float latitud, float longitud, String tipo, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipo = tipo;
        this.salas = salas;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlias() {
        return alias;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

}
