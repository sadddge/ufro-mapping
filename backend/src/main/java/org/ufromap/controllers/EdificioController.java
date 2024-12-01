package org.ufromap.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.dto.request.EdificioRequestDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.LocationDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.services.ISalaService;
import org.ufromap.services.impl.EdificioServiceImpl;
import org.ufromap.services.impl.SalaServiceImpl;

@WebServlet("/api/edificios/*")
public class EdificioController extends CrudController<EdificioDTO, EdificioRequestDTO, Edificio> {
    private final ISalaService salaService;

    public EdificioController() {
        super(new EdificioServiceImpl());
        salaService = new SalaServiceImpl();
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length == 3) {
            if ("map".equals(pathParts[1]) && "locations".equals(pathParts[2])) {
                handleLocationsRequest(response);
            } else if ("salas".equals(pathParts[2])) {
                handleSalasByEdificioIdRequest(pathParts, response);
            } else {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            }
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleLocationsRequest(HttpServletResponse response) throws IOException {
        List<LocationDTO> locations = ((EdificioServiceImpl) service).findAllLocations();
        writeJsonResponse(response, gson.toJson(locations));
    }

    private void handleSalasByEdificioIdRequest(String[] pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart[1]);
            String salas = pathPart[2];
            List<SalaDTO> salasDTO = salaService.getSalasByEdificioId(id);
            writeJsonResponse(response, gson.toJson(salasDTO));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected EdificioRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new EdificioRequestDTO(
                jsonObject.optString("nombre", null),
                jsonObject.optString("alias", null),
                jsonObject.optString("tipo", null),
                jsonObject.optFloat("latitud", -1),
                jsonObject.optFloat("longitud", -1)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"nombre", "alias", "tipo"};
    }
}