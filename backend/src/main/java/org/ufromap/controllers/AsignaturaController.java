package org.ufromap.controllers;

import org.ufromap.models.Asignatura;
import org.ufromap.services.AsignaturaService;

/**
 * Controlador que maneja las solicitudes relacionadas con la entidad "Asignatura".
 * 
 */
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    /**
     * Constructor que inicializa el controlador con el servicio de Asignatura.
     * @param asignaturaService el servicio que maneja la lógica de negocio relacionada con Asignatura.
     */
    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    /**
     * Registra una nueva asignatura utilizando el servicio de Asignatura.
     * @param asignatura el objeto Asignatura que se desea registrar.
     */
    public void registrarAsignatura(Asignatura asignatura) {
        asignaturaService.registrarAsignatura(asignatura);
    }
}