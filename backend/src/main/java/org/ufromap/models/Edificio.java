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
    private Float latitud;
    private Float longitud;
    private List<Sala> salas;

    /**
     * Constructor que inicializa un edificio con los datos proporcionados.
     * 
     * @param id El identificador único del edificio.
     * @param nombre El nombre completo del edificio.
     * @param alias Un alias o nombre corto del edificio.
     * @param latitud La coordenada de latitud del edificio.
     * @param longitud La coordenada de longitud del edificio.
     * @param tipo El tipo de edificio (por ejemplo, "académico", "administrativo").
     * @param salas Una lista de objetos {@link Sala} que pertenecen al edificio.
     */
    public Edificio(int id, String nombre, String alias, String tipo, Float latitud, Float longitud, List<Sala> salas) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.salas = salas;
    }

    /**
     * Obtiene el identificador único del edificio.
     * 
     * @return El ID del edificio.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del edificio.
     * 
     * @param id El nuevo ID del edificio.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del edificio.
     * 
     * @return El nombre del edificio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del edificio.
     * 
     * @param nombre El nuevo nombre del edificio.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el alias o nombre corto del edificio.
     * 
     * @return El alias del edificio.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Establece el alias o nombre corto del edificio.
     * 
     * @param alias El nuevo alias del edificio.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene la coordenada de latitud del edificio.
     * 
     * @return La latitud del edificio.
     */
    public Float getLatitud() {
        return latitud;
    }

    /**
     * Establece la coordenada de latitud del edificio.
     * 
     * @param latitud La nueva latitud del edificio.
     */
    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    /**
     * Obtiene la coordenada de longitud del edificio.
     * 
     * @return La longitud del edificio.
     */
    public Float getLongitud() {
        return longitud;
    }

    /**
     * Establece la coordenada de longitud del edificio.
     * 
     * @param longitud La nueva longitud del edificio.
     */
    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    /**
     * Obtiene el tipo de edificio (por ejemplo, "académico", "administrativo").
     * 
     * @return El tipo del edificio.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de edificio (por ejemplo, "académico", "administrativo").
     * 
     * @param tipo El nuevo tipo de edificio.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la lista de salas que pertenecen al edificio.
     * 
     * @return Una lista de objetos {@link Sala} asociados al edificio.
     */
    public List<Sala> getSalas() {
        return salas;
    }

    /**
     * Establece la lista de salas que pertenecen al edificio.
     * 
     * @param salas La nueva lista de objetos {@link Sala} asociados al edificio.
     */
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}
