package org.ufromap.controllers;

import org.ufromap.models.Clase;
import org.ufromap.services.ClaseService;

import javax.servlet.annotation.WebServlet;

import org.json.JSONObject;

/**
 * Controlador que maneja las solicitudes relacionadas con la entidad Clase.
 * Este controlador se comunica con el servicio {@link ClaseService} para procesar las
 * operaciones relacionadas con las clases.
 */
@WebServlet("/api/clases/*")
public class ClaseController extends BaseController<Clase> {

    public ClaseController() {
        super(new ClaseService());
    }

    @Override
    protected Clase mapJsonToEntity(JSONObject jsonObject) {
        return new Clase(
                jsonObject.optInt("id", -1),
                jsonObject.optInt("salaId", -1),
                jsonObject.optInt("edificioId", -1),
                jsonObject.optInt("asignaturaId", -1),
                jsonObject.optInt("diaSemana", -1),
                jsonObject.optInt("periodo", -1),
                jsonObject.optString("docente", null),
                jsonObject.optInt("modulo", -1)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"salaId", "edificioId", "asignaturaId", "diaSemana", "periodo", "docente", "modulo"};
    }
}