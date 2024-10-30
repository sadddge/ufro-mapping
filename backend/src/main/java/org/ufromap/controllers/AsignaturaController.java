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


}