package org.ufromap.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
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
        Clase clase = claseRepository.findById(id);
        if (clase == null) {
            throw new EntityNotFoundException("No se encontró una clase con el ID proporcionado.");
        }
        return clase;
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
        List<Clase>clases = claseRepository.findByFilter(filters);
        if (clases.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron clases con los filtros proporcionados.");
        }
        return clases;
    }

    /**
     * Método que devuelve todas las clases almacenadas en la base de datos a partir de un id de asignatura.
     *
     * @param asignaturaId El id de la asignatura de la que se desean recuperar las clases.
     * @return Una lista con todas las clases almacenadas en la base de datos de la asignatura con el id proporcionado.
     */
    public List<Clase> findByAsignaturaId(int asignaturaId) {
        List<Clase> clase = claseRepository.findByAsignaturaId(asignaturaId);
        if (clase == null) {
            throw new EntityNotFoundException("No se encontró una clase con el ID de la asignatura proporcionada.");
        }
        return clase;
    }

    public List<Clase> findBySalaId(int salaId) {
        List<Clase> clase = claseRepository.findBySalaId(salaId);
        if (clase == null) {
            throw new EntityNotFoundException("No se encontró una clase con el ID de la sala proporcionada.");
        }
        return clase;
    }

    /**
     * Método que almacena una clase en la base de datos.
     *
     * @param clase La clase que se desea almacenar en la base de datos.
     */
    public Clase add(Clase clase) {
        validateClase(clase);
        return claseRepository.add(clase);
    }


    /**
     * Método que actualiza una clase en la base de datos.
     *
     * @param clase La clase que se desea actualizar en la base de datos.
     */
    public Clase update(Clase clase) {
        validateClase(clase);
        clase = updateClase(clase);
        return claseRepository.update(clase);
    }

    /**
     * Método que elimina una clase de la base de datos.
     *
     * @param id El id de la clase que se desea eliminar de la base de datos.
     */
    public boolean delete(int id) {
        findById(id);
        return claseRepository.delete(id);
    }

    public void validateClase (Clase clase){
        if (clase.getDiaSemana() < 1 || clase.getDiaSemana() > 7) {
            throw new BadRequestException("El día de la semana debe ser un número entre 1 y 7.");
        }
        if (clase.getPeriodo() < 1 || clase.getPeriodo() > 11) {
            throw new BadRequestException("El periodo debe ser un número entre 1 y 11.");
        }
        if (clase.getModulo() < 1) {
            throw new BadRequestException("El módulo no puede ser menor a 1");
        }

    }

    private Clase updateClase(Clase clase) {
        Clase claseExistente = findById(clase.getId());
        String docente = clase.getDocente() == null ? claseExistente.getDocente() : clase.getDocente();
        int modulo = clase.getModulo() == -1 ? claseExistente.getModulo() : clase.getModulo();
        return new Clase(clase.getId(), clase.getSalaId(), clase.getEdificioId(), clase.getAsignaturaId(), clase.getDiaSemana(), clase.getPeriodo(), docente, modulo);

    }
}