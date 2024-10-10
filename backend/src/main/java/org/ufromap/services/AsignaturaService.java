package org.ufromap.services;


import java.sql.SQLException;

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

    public Asignatura getAsignaturaById(int id) throws SQLException {
        return asignaturaRepository.getAsignaturaById(id);
    }

    public void addAsignatura(String asignatura, String codigo, String descripcion) {
        asignaturaRepository.addAsignatura(asignatura, codigo, descripcion);
    }

    public void updateAsignatura(String asignatura, String codigo, String descripcion, int asignatura_id) {
        asignaturaRepository.updateAsignatura(asignatura, codigo, descripcion, asignatura_id);
    }

    public void deleteAsignatura(int asignatura_id) {
        asignaturaRepository.deleteAsignatura(asignatura_id);
    }


}