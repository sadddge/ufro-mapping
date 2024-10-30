package org.ufromap.services;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;

/**
 * Servicio que maneja la lógica de negocio relacionada con la entidad "Asignatura".
 * Utiliza el repositorio de Asignatura para realizar las operaciones CRUD y otras funcionalidades.
 */
public class AsignaturaService {

    private AsignaturaRepository asignaturaRepository;

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

    public Asignatura findById(int id) {
        return asignaturaRepository.findById(id);
    }

    public List<Asignatura> findByFilter(String nombre_asignatura, String codigo_asignatura, String descripcion_asignatura, Integer sct_asignatura) {
        Map<String, Object> filter = new HashMap<>();

        if (nombre_asignatura != null) filter.put("nombre_asignatura", nombre_asignatura);
        if (codigo_asignatura != null) filter.put("codigo_asignatura", codigo_asignatura);
        if (descripcion_asignatura != null) filter.put("descripcion_asignatura", descripcion_asignatura);
        if (sct_asignatura != null) filter.put("sct_asignatura", sct_asignatura);

        return asignaturaRepository.findByFilter(filter);
    }

    public Asignatura add(Asignatura asignatura) {
        return asignaturaRepository.add(asignatura);
    }

    public Asignatura update(Asignatura asignatura) {
        return asignaturaRepository.update(asignatura);
    }

    public boolean delete(int id) {
        return asignaturaRepository.delete(id);
    }

}