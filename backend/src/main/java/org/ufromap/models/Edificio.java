package org.ufromap.models;

import java.util.List;

public class Edificio{
    private final int id;
    private final String nombre;
    private final String alias;
    private final String ubicacion;
    private final String tipo;
    private final List<Sala> salas;

    public Edificio(int id, String nombre, String alias, String ubicacion, String tipo, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.ubicacion = ubicacion;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Sala> getSalas() {
        return salas;
    }
}
