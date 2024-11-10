package org.ufromap.services;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Inscripcion;
import org.ufromap.repositories.AsignaturaRepository;

/**
 * Servicio que maneja la lógica de negocio relacionada con la entidad "Asignatura".
 * Utiliza el repositorio de Asignatura para realizar las operaciones CRUD y otras funcionalidades.
 */
public class AsignaturaService implements IService<Asignatura> {

    private final AsignaturaRepository asignaturaRepository;

    /**
     * Constructor por defecto que inicializa el servicio con un nuevo repositorio de Asignatura.
     */
    public AsignaturaService() {
        this.asignaturaRepository = new AsignaturaRepository();
    }

    @Override
    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    @Override
    public Asignatura findById(int id) throws EntityNotFoundException {
        Asignatura asignatura = asignaturaRepository.findById(id);
        if (asignatura == null)
            throw new EntityNotFoundException("No se encontró la asignatura con el ID proporcionado");
        return asignatura;
    }

    @Override
    public List<Asignatura> findByFilter(Map<String, Object> filter) throws EntityNotFoundException {
        List<Asignatura> asignaturas = asignaturaRepository.findByFilter(filter);
        if (asignaturas.isEmpty())
            throw new EntityNotFoundException("No se encontraron asignaturas con los filtros proporcionados");
        return asignaturas;
    }

    @Override
    public Asignatura add(Asignatura asignatura) throws BadRequestException {
        validateAsignatura(asignatura);
        return asignaturaRepository.add(asignatura);
    }

    @Override
    public Asignatura update(Asignatura asignatura) {
        validateAsignatura(asignatura);
        return asignaturaRepository.update(asignatura);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        findById(id);
        asignaturaRepository.delete(id);
    }

    public void validateAsignatura(Asignatura asignatura) {
        if (asignatura.getNombre() == null || asignatura.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la asignatura es obligatorio.");
        }
        if (asignatura.getCodigo() == null || asignatura.getCodigo().isEmpty()) {
            throw new BadRequestException("El codigo de la asignatura es obligatorio.");
        }
    }

    public Set<Asignatura> getAsignaturasByInscripciones(List<Inscripcion> inscripciones) {
        Set<Asignatura> asignaturas = new HashSet<Asignatura>();
        for (Inscripcion inscripcion : inscripciones) {
            asignaturas.add(findById(inscripcion.getAsignaturaId()));
        }
        return asignaturas;
    }

}