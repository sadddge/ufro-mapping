package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Representa un edificio dentro del mapa del campus, con información sobre su nombre, ubicación, tipo y las salas que contiene.
 */
@Getter
@Setter
@AllArgsConstructor
public class Edificio {

    private int id;
    @SerializedName("nombre_edificio")
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
    private List<Sala> salas;
}