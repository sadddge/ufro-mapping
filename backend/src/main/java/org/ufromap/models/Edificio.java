package org.ufromap.models;

import java.util.List;

/**
 * Representa un edificio dentro del mapa del campus, con información sobre su nombre, ubicación, tipo y las salas que contiene.
 */
public class Edificio {

    private int id;
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;

    public Edificio(int id, String nombre, String alias, String tipo, float latitud, float longitud) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}