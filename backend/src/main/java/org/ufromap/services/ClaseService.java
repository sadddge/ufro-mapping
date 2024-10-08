package org.ufromap.services;

import org.ufromap.repositories.ClaseRepository;
import org.ufromap.Clase;

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
}