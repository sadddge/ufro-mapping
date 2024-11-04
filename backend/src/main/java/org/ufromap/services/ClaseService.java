package org.ufromap.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

/**
 * Servicio que proporciona métodos para gestionar la entidad Clase a través del repositorio {@link ClaseRepository}.
 */
public class ClaseService {
    private final ClaseRepository claseRepository;

    /**
     * Constructor que inicializa el servicio con un repositorio de clases.
     * 
     * @param claseRepository El repositorio de la clase {@link ClaseRepository} que se utilizará para las operaciones.
     */
    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public ClaseService() {
        this.claseRepository = new ClaseRepository();
    }
    /**
     * Método que devuelve todas las clases almacenadas en la base de datos.
     *
     * @return Una lista con todas las clases almacenadas en la base de datos.
     */
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    /**
     * Método que devuelve una clase almacenada en la base de datos a partir de su id.
     *
     * @param id El id de la clase que se desea recuperar.
     * @return La clase almacenada en la base de datos con el id proporcionado.
     */
    public Clase findById(int id) {
        return claseRepository.findById(id);
    }

    public List<Clase> findByFilter(Integer salaId, Integer edificioId, Integer asignaturaId, Integer diaSemana, Integer periodo, String docente, Integer modulo) {
        Map<String, Object> filters = new HashMap<>();
        if (salaId != null) filters.put("salaId", salaId);
        if (edificioId != null) filters.put("edificioId", edificioId);
        if (asignaturaId != null) filters.put("asignaturaId", asignaturaId);
        if (diaSemana != null) filters.put("diaSemana", diaSemana);
        if (periodo != null) filters.put("periodo", periodo);
        if (docente != null) filters.put("docente", docente);
        if (modulo != null) filters.put("modulo", modulo);
        return claseRepository.findByFilter(filters);
    }

    /**
     * Método que devuelve todas las clases almacenadas en la base de datos a partir de un id de asignatura.
     *
     * @param asignaturaId El id de la asignatura de la que se desean recuperar las clases.
     * @return Una lista con todas las clases almacenadas en la base de datos de la asignatura con el id proporcionado.
     */
    public List<Clase> findByAsignaturaId(int asignaturaId) {
        return claseRepository.findByAsignaturaId(asignaturaId);
    }

    public List<Clase> findBySalaId(int salaId) {
        return claseRepository.findBySalaId(salaId);
    }

    /**
     * Método que almacena una clase en la base de datos.
     *
     * @param clase La clase que se desea almacenar en la base de datos.
     */
    public Clase add(Clase clase) {
        return claseRepository.add(clase);
    }


    /**
     * Método que actualiza una clase en la base de datos.
     *
     * @param clase La clase que se desea actualizar en la base de datos.
     */
    public Clase update(Clase clase) {
        return claseRepository.update(clase);
    }

    /**
     * Método que elimina una clase de la base de datos.
     *
     * @param id El id de la clase que se desea eliminar de la base de datos.
     */
    public boolean delete(int id) {
        return claseRepository.delete(id);
    }
}