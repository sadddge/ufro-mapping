package org.ufromap.services;

import java.util.List;

import org.ufromap.models.Clase;
import org.ufromap.repositories.ClaseRepository;

public class ClaseService {
    private final ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public void getAllClases(){
        claseRepository.getClases();
    }

    public Clase getClaseById(int id){
        return claseRepository.getClaseById(id);
    }

    public List<Clase> getClasesBySalaId(int sala_id){
        return claseRepository.getClasesBySalaId(sala_id);
    }

    public List<Clase> getClasesByEdificioId(int edificio_id){
        return claseRepository.getClasesByEdificioId(edificio_id);
    }

    public List<Clase> getClasesByAsignaturaId(int asignatura_id){
        return claseRepository.getClasesByAsignaturaId(asignatura_id);
    }

    


}