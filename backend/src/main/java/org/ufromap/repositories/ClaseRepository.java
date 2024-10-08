package org.ufromap.repositories;
import java.util.List;

import org.ufromap.Clase;

public class ClaseRepository {

    private List<Clase> clases;

    public ClaseRepository(List<Clase> clases) {
        this.clases = clases;
    }

    public List<Clase> getClases() {
        return clases;
    }


}