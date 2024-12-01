package org.ufromap.controllers;

import org.json.JSONObject;
import org.ufromap.models.Sala;
import org.ufromap.services.SalaService;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/salas/*")
public class SalaController extends BaseController<Sala> {
    public SalaController() {
        super(new SalaService());
    }

    @Override
    protected Sala mapJsonToEntity(JSONObject jsonObject) {
        return new Sala(
                jsonObject.optInt("id", -1),
                jsonObject.optInt("edificioId", -1),
                jsonObject.optString("nombre", null),
                null
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"edificioId", "nombre"};
    }
}