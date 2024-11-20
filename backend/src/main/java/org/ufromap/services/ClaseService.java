package org.ufromap.services;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

/**
 * Servicio que proporciona métodos para gestionar la entidad Clase a través del repositorio {@link ClaseRepository}.
 */
@AllArgsConstructor
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
    @Override
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    @Override
    public Clase findById(int id) {
        Clase clase = claseRepository.findById(id);
        if (clase == null) throw new EntityNotFoundException("No se encontró una clase con el ID proporcionado.");
        return clase;
    }

    @Override
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
    @Override
    public Clase add(Clase clase) {
        validateClase(clase);
        return claseRepository.add(clase);
    }


    /**
     * Método que actualiza una clase en la base de datos.
     *
     * @param clase La clase que se desea actualizar en la base de datos.
     */
    @Override
    public Clase update(Clase clase) {
        validateClase(clase);
        return claseRepository.update(clase);
    }

    @Override
    public Clase patch(Clase entity, JSONObject jsonObject) throws EntityNotFoundException {
        Clase clase = new Clase();
        int salaId = jsonObject.optInt("salaId", entity.getSalaId());
        int edificioId = jsonObject.optInt("edificioId", entity.getEdificioId());
        int asignaturaId = jsonObject.optInt("asignaturaid", entity.getAsignaturaId());
        int diaSemana = jsonObject.optInt("diasemana", entity.getDiaSemana());
        int periodo = jsonObject.optInt("periodo", entity.getPeriodo());
        String docente = jsonObject.optString("docente", entity.getDocente());
        int modulo = jsonObject.optInt("modulo", entity.getModulo());
        clase.setId(entity.getId());
        clase.setSalaId(salaId);
        clase.setEdificioId(edificioId);
        clase.setAsignaturaId(asignaturaId);
        clase.setDiaSemana(diaSemana);
        clase.setPeriodo(periodo);
        clase.setDocente(docente);
        clase.setModulo(modulo);
        return update(clase);
    }

    @Override
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
}