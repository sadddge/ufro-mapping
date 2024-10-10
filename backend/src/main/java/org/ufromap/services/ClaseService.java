package org.ufromap.services;

import org.ufromap.Clase;
import org.ufromap.repositories.ClaseRepository;

public class ClaseService {
    private ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public void mostrarInformacionDeClases() {
        for (Clase clase : claseRepository.getClases()) {
            clase.mostrarInformacion();
        }
    }

    public void getAllClases(){
        claseRepository.getAllClases();
    }

    public Clase getClaseById(int id){
        return claseRepository.getClaseById(id);
    }

    public Clase getClaseBySalaId(int sala_id){
        return claseRepository.getClaseBySalaId(sala_id);
    }

    public Clase getClaseByEdificioId(int edificio_id){
        return claseRepository.getClaseByEdificioId(edificio_id);
    }

    public Clase getClaseByAsignaturaId(int asignatura_id){
        return claseRepository.getClaseByAsignaturaId(asignatura_id);
    }

    


}