package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;
import org.ufromap.services.SalaService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/api/salas/*")
public class SalaController extends BaseController<Sala>{
    private final SalaService salaService = new SalaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response, salaService::findById);
                break;
            case 0:
                if (!params.isEmpty()) {
                    System.out.println("entro");
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response, salaService::findAll);
                }
                break;
            default:
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePut(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        handleDelete(request, response);
    }

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> filters = new HashMap<>();
        String[] validFilters = {"edificio_id", "nombre_sala"};

        for (String filter : validFilters) {
            if (request.getParameter(filter) != null) {
                filters.put(filter, request.getParameter(filter));
            }
        }

        if (filters.isEmpty()) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing filters");
            return;
        }

        List<Sala> salas = salaService.findByFilter(filters);
        writeJsonResponse(response, new Gson().toJson(salas));
    }

    @Override
    protected Sala processPost(Sala entity) {
        return salaService.add(entity);
    }

    @Override
    protected Sala processPut(Sala entity) throws EntityNotFoundException {
        return salaService.update(entity);
    }

    @Override
    protected void processDelete(int id) throws EntityNotFoundException {
        salaService.delete(id);
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
}
