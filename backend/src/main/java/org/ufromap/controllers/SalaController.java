package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
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
public class SalaController extends BaseController{
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
        try {
            JSONObject jsonObject = getJson(request);
            Sala sala = getSalaFromJson(jsonObject);
            Sala updated = salaService.add(sala);
            writeJsonResponse(response, new Gson().toJson(updated));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            Sala sala = getSalaFromJson(jsonObject);
            Sala updated = salaService.update(sala);
            writeJsonResponse(response, new Gson().toJson(updated));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");
        if (pathParts.length != 2) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }

        try {
            int id = Integer.parseInt(pathParts[1]);
            salaService.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
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

    private Sala getSalaFromJson(JSONObject jsonObject) {
        int id = jsonObject.optInt("id", -1);
        int edificioId = jsonObject.optInt("edificioId", -1);
        String nombre = jsonObject.optString("nombre", null);

        return new Sala(id, edificioId, nombre, null);
    }
}
