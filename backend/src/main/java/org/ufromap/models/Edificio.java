package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Representa un edificio dentro del mapa del campus, con información sobre su nombre, ubicación, tipo y las salas que contiene.
 */
@Getter
@Setter
public class Edificio {

    private int id;
    @SerializedName("nombre_edificio")
    private String nombre;
    private String alias;
    private String tipo;
    private float latitud;
    private float longitud;
    private List<Sala> salas;

    /**
     * Constructor que inicializa un edificio con los datos proporcionados.
     *
     * @param id       El identificador único del edificio.
     * @param nombre   El nombre completo del edificio.
     * @param alias    Un alias o nombre corto del edificio.
     * @param latitud  La coordenada de latitud del edificio.
     * @param longitud La coordenada de longitud del edificio.
     * @param tipo     El tipo de edificio (por ejemplo, "académico", "administrativo").
     * @param salas    Una lista de objetos {@link Sala} que pertenecen al edificio.
     */
    public Edificio(int id, String nombre, String alias, String tipo, float latitud, float longitud, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.salas = salas;
    }
}
