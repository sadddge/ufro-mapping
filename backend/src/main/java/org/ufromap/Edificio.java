package org.ufromap;

import java.util.List;

import org.ufromap.models.Sala;

public class Edificio implements InfoMostrable, ClaseMostrable {
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

    @Override
    public void mostrarClases() {

    }

    @Override
    public void mostrarInformacion() {

    }
}
