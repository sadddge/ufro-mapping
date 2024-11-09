package org.ufromap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.services.EdificioService;

import com.google.gson.Gson;


@WebServlet("/api/edificios/*")
public class EdificioController extends BaseController<Edificio> {
    private final EdificioService edificioService = new EdificioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response, edificioService::findById);
                break;
            case 3:
                handleSalasByEdificioIdRequest(pathParts, response);
                break;
            case 0:
                if (!params.isEmpty()) {
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response, edificioService::findAll);
                }
                break;
            default:
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> filters = new HashMap<>();
        String[] validFilters = {"nombre_edificio", "alias_edificio", "tipo_edificio", "latitud", "longitud"};

        for (String filter : validFilters) {
            if (request.getParameter(filter) != null) {
                filters.put(filter, request.getParameter(filter));
            }
        }

        if (filters.isEmpty()) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing filters");
            return;
        }

        List<Edificio> edificios = edificioService.findByFilter(filters);
        writeJsonResponse(response, new Gson().toJson(edificios));
    }


    private void handleSalasByEdificioIdRequest(String[] pathPart, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathPart[1]);
            String salas = pathPart[2];
            if (!salas.equals("salas")) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
                return;
            }
            Edificio edificio = edificioService.findById(id);
            writeJsonResponse(response, new Gson().toJson(edificio.getSalas()));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){
        handleDelete(request, response);
    }

    @Override
    protected Edificio processPost(Edificio entity) throws BadRequestException {
        return edificioService.add(entity);
    }

    @Override
    protected Edificio processPut(Edificio entity) throws EntityNotFoundException {
        return edificioService.update(entity);
    }

    @Override
    protected void processDelete(int id) throws EntityNotFoundException {
        edificioService.delete(id);
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
}
