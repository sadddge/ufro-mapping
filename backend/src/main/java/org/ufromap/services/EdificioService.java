package org.ufromap.services;

import java.util.List;

import org.ufromap.models.Edificio;
import org.ufromap.repositories.EdificioRepository;

/**
 * Servicio que maneja la lógica de negocio para la entidad {@link Edificio}.
 * Se encarga de interactuar con el repositorio de edificios {@link EdificioRepository} 
 * y ofrece métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) 
 * sobre los edificios.
 */
public class EdificioService {

    private EdificioRepository edificioRepository;

    /**
     * Constructor por defecto que inicializa el repositorio de edificios.
     */
    public EdificioService() {
        this.edificioRepository = new EdificioRepository();
    }

    /**
     * Constructor que permite inyectar una instancia de {@link EdificioRepository}.
     *
     * @param edificioRepository El repositorio de edificios a utilizar.
     */
    public EdificioService(EdificioRepository edificioRepository) {
        this.edificioRepository = edificioRepository;
    }

    /**
     * Obtiene todos los edificios registrados en la base de datos.
     *
     * @return Una lista de objetos {@link Edificio} que contiene todos los edificios.
     */
    public List<Edificio> getEdificios() {
        return edificioRepository.getEdificios();
    }

    /**
     * Obtiene un edificio específico por su ID.
     *
     * @param id El ID del edificio a buscar.
     * @return El objeto {@link Edificio} correspondiente al ID proporcionado, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioById(int id) {
        return edificioRepository.getEdificioById(id);
    }

    /**
     * Busca un edificio por su nombre.
     *
     * @param nombre El nombre del edificio.
     * @return El objeto {@link Edificio} correspondiente al nombre proporcionado, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByNombre(String nombre) {
        return edificioRepository.getEdificioByNombre(nombre);
    }

    /**
     * Busca un edificio por su alias.
     *
     * @param alias El alias del edificio.
     * @return El objeto {@link Edificio} correspondiente al alias proporcionado, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByAlias(String alias) {
        return edificioRepository.getEdificioByAlias(alias);
    }

    /**
     * Busca un edificio por su tipo.
     *
     * @param tipo El tipo de edificio (por ejemplo, académico, administrativo, etc.).
     * @return El objeto {@link Edificio} correspondiente al tipo proporcionado, o {@code null} si no se encuentra.
     */
    public Edificio getEdificioByTipo(String tipo) {
        return edificioRepository.getEdificioByTipo(tipo);
    }

    /**
     * Agrega un nuevo edificio a la base de datos.
     *
     * @param edificio El objeto {@link Edificio} con los datos del nuevo edificio.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean addEdificio(Edificio edificio) {
        return edificioRepository.addEdificio(edificio);
    }

    /**
     * Actualiza los datos de un edificio existente.
     *
     * @param edificio El objeto {@link Edificio} con los datos actualizados.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean updateEdificio(Edificio edificio) {
        return edificioRepository.updateEdificio(edificio);
    }

    /**
     * Elimina un edificio de la base de datos por su ID.
     *
     * @param id El ID del edificio a eliminar.
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió algún error.
     */
    public boolean deleteEdificio(int id) {
        return edificioRepository.deleteEdificio(id);
    }
    
}
