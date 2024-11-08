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
        String nombre = request.getParameter("nombre");
        Integer edificioId;

        try {
            edificioId = request.getParameter("edificio_id") == null ? null : Integer.parseInt(request.getParameter("edificio_id"));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid edificio_id");
            return;
        }

        if (edificioId != null || nombre != null) {
            try {
                List<Sala> list = salaService.findByFilter(edificioId, nombre);
                writeJsonResponse(response, new Gson().toJson(list));
            } catch (EntityNotFoundException e) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }
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
        int id = jsonObject.optInt("id", -1);
        int edificioId = jsonObject.optInt("edificio_id", -1);
        String nombre = jsonObject.optString("nombre", null);
        return new Sala(id, edificioId, nombre, null);
    }
}
