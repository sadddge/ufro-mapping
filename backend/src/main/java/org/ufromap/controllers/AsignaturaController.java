package org.ufromap.controllers;

import org.ufromap.models.AsignaturaModel;
import org.ufromap.services.AsignaturaService;

public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    public void registrarAsignatura(AsignaturaModel asignatura) {
        asignaturaService.registrarAsignatura(asignatura);
    }
}