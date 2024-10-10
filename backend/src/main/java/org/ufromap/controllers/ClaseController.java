package org.ufromap.controllers;

import org.ufromap.services.ClaseService;

public class ClaseController {
    private ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    public void mostrarInformacion() {
        claseService.mostrarInformacionDeClases();
    }
}