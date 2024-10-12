package org.ufromap.models;

import java.util.List;

/**
 * Clase que representa una sala dentro de un edificio en el sistema.
 * Una sala está asociada a un edificio y puede tener una lista de clases que se imparten en ella.
 */
public class Sala {
    private int id;
    private int edificioId;
    private String nombre;

    private List<Clase> clases;

    /**
     * Constructor que inicializa una instancia de la clase Sala con sus atributos.
     *
     * @param id       El identificador único de la sala.
     * @param nombre   El nombre de la sala.
     * @param edificio El edificio al que pertenece la sala.
     * @param clases   La lista de clases que se imparten en la sala.
     */
    public Sala(int id, String nombre, int edificio, List<Clase> clases) {
        this.id = id;
        this.nombre = nombre;
        this.edificioId = edificio;
        this.clases = clases;
    }

    /**
     * Obtiene el identificador único de la sala.
     *
     * @return El identificador de la sala.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la sala.
     *
     * @return El nombre de la sala.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el la id del edificio al que pertenece la sala.
     *
     * @return El edificio asociado a la sala.
     */
    public int getEdificioId() {
        return edificioId;
    }

    /**
     * Obtiene la lista de clases que se imparten en la sala.
     *
     * @return La lista de clases en la sala.
     */
    public List<Clase> getClases() {
        return clases;
    }

    /**
     * Establece el identificador único de la sala.
     *
     * @param id El nuevo identificador de la sala.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece el nombre de la sala.
     *
     * @param nombre El nuevo nombre de la sala.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la id del edificio al que pertenece la sala.
     *
     * @param edificioId El nuevo edificio asociado a la sala.
     */
    public void setEdificioId(int edificioId) {
        this.edificioId = edificioId;
    }

    /**
     * Establece la lista de clases que se imparten en la sala.
     *
     * @param clases La nueva lista de clases en la sala.
     */
    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
}
