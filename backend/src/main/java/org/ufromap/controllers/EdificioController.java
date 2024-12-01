package org.ufromap.controllers;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.services.EdificioService;


@WebServlet("/api/edificios/*")
public class EdificioController extends BaseController<Edificio> {
    public EdificioController() {
        super(new EdificioService());
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length == 3) {
            handleSalasByEdificioIdRequest(pathParts, response);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleSalasByEdificioIdRequest(String[] pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart[1]);
            String salas = pathPart[2];
            if (!salas.equals("salas")) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
                return;
            }
            Edificio edificio = service.findById(id);
            writeJsonResponse(response, gson.toJson(edificio.getSalas()));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected Edificio mapJsonToEntity(JSONObject jsonObject) {
        return new Edificio(
                jsonObject.optInt("id", -1),
                jsonObject.optString("nombre", null),
                jsonObject.optString("alias", null),
                jsonObject.optString("tipo", null),
                jsonObject.optFloat("latitud", -1),
                jsonObject.optFloat("longitud", -1),
                null
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"nombre", "alias", "tipo"};
    }
}