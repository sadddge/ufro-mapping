package org.ufromap.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;

/**
 * Servicio que maneja la lógica de negocio relacionada con la entidad "Asignatura".
 * Utiliza el repositorio de Asignatura para realizar las operaciones CRUD y otras funcionalidades.
 */
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    /**
     * Constructor que inicializa el servicio con un repositorio de Asignatura.
     * @param asignaturaRepository el repositorio para realizar las operaciones de base de datos sobre Asignatura.
     */
    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    /**
     * Constructor por defecto que inicializa el servicio con un nuevo repositorio de Asignatura.
     */
    public AsignaturaService() {
        this.asignaturaRepository = new AsignaturaRepository();
    }


    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    public Asignatura findById(int id) throws EntityNotFoundException{
        Asignatura asignatura = asignaturaRepository.findById(id);
        if(asignatura == null){
            throw new EntityNotFoundException("No se encontró la asignatura con el ID proporcionado");
        }
        return asignatura;
    }

    public List<Asignatura> findByFilter(String nombre_asignatura, String codigo_asignatura, String descripcion_asignatura, Integer sct_asignatura) throws EntityNotFoundException{
        Map<String, Object> filter = new HashMap<>();

        if (nombre_asignatura != null) filter.put("nombre_asignatura", nombre_asignatura);
        if (codigo_asignatura != null) filter.put("codigo_asignatura", codigo_asignatura);
        if (descripcion_asignatura != null) filter.put("descripcion_asignatura", descripcion_asignatura);
        if (sct_asignatura != null) filter.put("sct_asignatura", sct_asignatura);

        List<Asignatura> asignaturas = asignaturaRepository.findByFilter(filter);
        if(asignaturas.isEmpty()){
            throw new EntityNotFoundException("No se encontraron asignaturas con los filtros proporcionados");
        }

        return asignaturas;
    }

    public Asignatura add(Asignatura asignatura) throws BadRequestException{
        validateAsignatura(asignatura);
        return asignaturaRepository.add(asignatura);
    }

    public Asignatura update(Asignatura asignatura) {
        Asignatura asignaturaExistente = findById(asignatura.getId());
        String descripcion = asignatura.getDescripcion() == null ? asignaturaExistente.getDescripcion() : asignatura.getDescripcion();
        String codigo =  asignatura.getCodigo() == null ? asignaturaExistente.getCodigo() : asignatura.getCodigo();
        int sct =  asignatura.getSct() == 0 ? asignaturaExistente.getSct() : asignatura.getSct();
        return new Asignatura(asignatura.getId(), asignatura.getNombre(), codigo, descripcion, sct, asignatura.getClases());
    }

    public boolean delete(int id) throws EntityNotFoundException{
        findById(id);
        return asignaturaRepository.delete(id);
    }

    public void validateAsignatura(Asignatura asignatura) {
        if (asignatura.getNombre() == null || asignatura.getNombre().isEmpty()) {
            throw new BadRequestException("El nombre de la asignatura es obligatorio.");
        }
        if (asignatura.getCodigo() == null || asignatura.getCodigo().isEmpty()) {
            throw new BadRequestException("El codigo de la asignatura es obligatorio.");
        }
    }

}