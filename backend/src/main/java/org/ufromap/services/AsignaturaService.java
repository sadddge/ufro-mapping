package org.ufromap.services;


import org.ufromap.models.Asignatura;
import org.ufromap.repositories.AsignaturaRepository;

public class AsignaturaService {

    private AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public void registrarAsignatura(Asignatura asignatura) {

    }

    public Asignatura getAsignaturaByCodigo( String codigo) {
        return asignaturaRepository.getAsignaturaByCodigo(codigo);
    }

    public Asignatura getAsignaturaByNombre( String nombre) {
        return asignaturaRepository.getAsignaturaByNombre(nombre);
    }

    public Asignatura getAsignaturaById(int id) {
        return asignaturaRepository.getAsignaturaById(id);
    }

    public Asignatura addAsignatura(Asignatura asignatura) {
        return asignaturaRepository.addAsignatura(asignatura);
    }

    public Asignatura updateAsignatura(Asignatura asignatura) {
        return asignaturaRepository.updateAsignatura(asignatura);
    }

    public void deleteAsignatura(Asignatura asignatura) {
        asignaturaRepository.deleteAsignatura(asignatura);
    }


}