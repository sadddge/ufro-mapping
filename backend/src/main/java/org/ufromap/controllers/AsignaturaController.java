package org.ufromap.controllers;

import org.ufromap.models.Asignatura;
import org.ufromap.services.AsignaturaService;

public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    public void registrarAsignatura(Asignatura asignatura) {
        asignaturaService.registrarAsignatura(asignatura);
    }
}