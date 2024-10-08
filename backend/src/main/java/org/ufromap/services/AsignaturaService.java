package org.ufromap.services;


import org.ufromap.Usuario;
import org.ufromap.models.AsignaturaModel;
import org.ufromap.repositories.AsignaturaRepository;

public class AsignaturaService {

    private AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public void registrarAsignatura(AsignaturaModel asignatura) {

    }

    public AsignaturaModel getAsignaturaByCodigo(Usuario usuario, String codigo) {
        return asignaturaRepository.getAsignaturaByCodigo(usuario, codigo);
    }

    public AsignaturaModel getAsignaturaByNombre(Usuario usuario, String nombre) {
        return asignaturaRepository.getAsignaturaByNombre(usuario,nombre);
    }


}