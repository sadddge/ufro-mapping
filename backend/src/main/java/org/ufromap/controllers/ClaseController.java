package org.ufromap.controllers;

import org.ufromap.dto.request.ClaseRequestDTO;
import org.ufromap.dto.response.ClaseDTO;
import org.ufromap.models.Clase;

import javax.servlet.annotation.WebServlet;

import org.json.JSONObject;
import org.ufromap.services.impl.ClaseServiceImpl;

@WebServlet("/api/clases/*")
public class ClaseController extends CrudController<ClaseDTO, ClaseRequestDTO, Clase> {

    public ClaseController() {
        super(new ClaseServiceImpl());
    }

    @Override
    protected ClaseRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new ClaseRequestDTO(
                jsonObject.optInt("salaId", -1),
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