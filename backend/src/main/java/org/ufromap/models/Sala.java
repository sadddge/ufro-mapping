package org.ufromap.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Clase que representa una sala dentro de un edificio en el sistema.
 * Una sala está asociada a un edificio y puede tener una lista de clases que se imparten en ella.
 */
@Getter
@Setter
public class Sala {
    private int id;
    @SerializedName("edificio_id")
    private int edificioId;
    @SerializedName("nombre_sala")
    private String nombre;

    private List<Clase> clases;

    /**
     * Constructor que inicializa una instancia de la clase Sala con sus atributos.
     *
     * @param id         El identificador único de la sala.
     * @param nombre     El nombre de la sala.
     * @param edificioId El edificio al que pertenece la sala.
     * @param clases     La lista de clases que se imparten en la sala.
     */
    public Sala(int id, int edificioId, String nombre, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.edificioId = edificioId;
        this.clases = clases;
    }
}
