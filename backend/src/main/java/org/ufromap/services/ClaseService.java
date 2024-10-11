package org.ufromap.services;

import java.util.List;

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

    /**
     * Obtiene todas las clases de la base de datos.
     * Utiliza el repositorio para recuperar la lista completa de clases.
     */
    public void getAllClases(){
        claseRepository.getClases();
    }

    /**
     * Obtiene una clase específica por su ID.
     * 
     * @param id El ID de la clase que se quiere recuperar.
     * @return Un objeto {@link Clase} si se encuentra, o {@code null} si no existe.
     */
    public Clase getClaseById(int id){
        return claseRepository.getClaseById(id);
    }

    /**
     * Obtiene todas las clases que ocurren en una sala específica.
     * 
     * @param sala_id El ID de la sala.
     * @return Una lista de objetos {@link Clase} correspondientes a la sala indicada.
     */
    public List<Clase> getClasesBySalaId(int sala_id){
        return claseRepository.getClasesBySalaId(sala_id);
    }

    /**
     * Obtiene todas las clases que se dictan en un edificio específico.
     * 
     * @param edificio_id El ID del edificio.
     * @return Una lista de objetos {@link Clase} correspondientes al edificio indicado.
     */
    public List<Clase> getClasesByEdificioId(int edificio_id){
        return claseRepository.getClasesByEdificioId(edificio_id);
    }

    /**
     * Obtiene todas las clases de una asignatura específica.
     * 
     * @param asignatura_id El ID de la asignatura.
     * @return Una lista de objetos {@link Clase} correspondientes a la asignatura indicada.
     */
    public List<Clase> getClasesByAsignaturaId(int asignatura_id){
        return claseRepository.getClasesByAsignaturaId(asignatura_id);
    }
}