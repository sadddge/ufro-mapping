package org.ufromap.controllers;

import java.io.IOException;
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
    private final EdificioService edificioService;
    public EdificioController() {
        this.edificioService = new EdificioService();
    }
    public EdificioController(EdificioService edificioService) {
        this.edificioService = edificioService;
    }

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
        String nombre = request.getParameter("nombre");
        String alias = request.getParameter("alias");
        String tipo = request.getParameter("tipo");
        Float latitud;
        Float longitud;
        try {
            latitud = request.getParameter("latitud") == null ? null : Float.parseFloat(request.getParameter("latitud"));
            longitud = request.getParameter("longitud") == null ? null : Float.parseFloat(request.getParameter("longitud"));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        if (nombre != null || alias != null || tipo != null || latitud != null || longitud != null) {
            List<Edificio> edificios = edificioService.findByFilter(nombre, alias, tipo, latitud, longitud);
            response.getWriter().print(new Gson().toJson(edificios));
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
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
        int id = jsonObject.optInt("id", -1);
        String nombre = jsonObject.optString("nombre", null);
        String alias = jsonObject.optString("alias", null);
        String tipo = jsonObject.optString("tipo", null);
        float latitud = jsonObject.optFloat("latitud", 0);
        float longitud = jsonObject.optFloat("longitud", 0);
        return new Edificio(id, nombre, alias, tipo, latitud, longitud, null);
    }
}
