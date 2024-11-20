package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

/**
 * Representa un edificio dentro del mapa del campus, con información sobre su nombre, ubicación, tipo y las salas que contiene.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edificio {

    private int id;
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
    private List<Sala> salas;
}