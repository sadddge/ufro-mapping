package org.ufromap.services;

import java.util.List;
import java.util.Map;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

/**
 * Servicio que proporciona métodos para gestionar la entidad Clase a través del repositorio {@link ClaseRepository}.
 */
public class ClaseService implements IService<Clase> {
    private final ClaseRepository claseRepository;


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

    public Clase findById(int id) {
        Clase clase = claseRepository.findById(id);
        if (clase == null) throw new EntityNotFoundException("No se encontró una clase con el ID proporcionado.");
        return clase;
    }

    public List<Clase> findByFilter(Map<String, Object> filters) {
        List<Clase> clases = claseRepository.findByFilter(filters);
        if (clases.isEmpty())
            throw new EntityNotFoundException("No se encontraron clases con los filtros proporcionados.");
        return clases;
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

    public void delete(int id) {
        findById(id);
        claseRepository.delete(id);
    }

    public void validateClase(Clase clase) {
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