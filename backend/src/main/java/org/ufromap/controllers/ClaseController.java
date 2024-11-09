package org.ufromap.controllers;

import org.ufromap.models.Clase;
import org.ufromap.services.ClaseService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador que maneja las solicitudes relacionadas con la entidad Clase.
 * Este controlador se comunica con el servicio {@link ClaseService} para procesar las
 * operaciones relacionadas con las clases.
 */
@WebServlet("/api/clases/*")
public class ClaseController extends BaseController<Clase> {

    private final ClaseService claseService = new ClaseService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");
        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response, claseService::findById);
                break;
            case 0:
                if (!params.isEmpty()) {
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response, claseService::findAll);
                }
                break;
            default:
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> filters = new HashMap<>();
        String[] validFilters = {"sala_id", "edificio_id", "asignatura_id", "dia_semana", "periodo", "docente", "modulo"};

        for (String filter : validFilters) {
            if (request.getParameter(filter) != null) {
                filters.put(filter, request.getParameter(filter));
            }
        }

        if (filters.isEmpty()) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing filters");
            return;
        }

        List<Clase> clases = claseService.findByFilter(filters);
        writeJsonResponse(response, new Gson().toJson(clases));
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePost(request, response);
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePut(request, response);
    }

    @Override
    protected void doDelete (HttpServletRequest request, HttpServletResponse response) {
        handleDelete(request, response);
    }

    @Override
    protected Clase processPost(Clase entity) throws BadRequestException {
        return claseService.add(entity);
    }

    @Override
    protected Clase processPut(Clase entity) throws EntityNotFoundException {
        return claseService.update(entity);
    }

    @Override
    protected void processDelete(int id) throws EntityNotFoundException {
        claseService.delete(id);
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
}