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
                jsonObject.optInt("sala_id", -1),
                jsonObject.optInt("edificio_id", -1),
                jsonObject.optInt("asignatura_id", -1),
                jsonObject.optInt("dia_semana", -1),
                jsonObject.optInt("periodo", -1),
                jsonObject.optString("docente_nombre", null),
                jsonObject.optInt("modulo", -1)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"sala_id", "edificio_id", "asignatura_id", "dia_semana", "periodo", "docente_nombre", "modulo"};
    }
}