package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.services.AsignaturaService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/asignaturas/*")
public class AsignaturaController extends BaseController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController() {
        this.asignaturaService = new AsignaturaService();
    }

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response, asignaturaService::findById);
                break;
            case 3:
                handleClasesByAsignaturaIdRequest(pathParts, response);
                break;
            case 0:
                if (!params.isEmpty()) {
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response, asignaturaService::findAll);
                }
                break;
            default:
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            Asignatura asignatura = getAsignaturaFromJson(jsonObject);
            Asignatura created = asignaturaService.add(asignatura);
            writeJsonResponse(response, new Gson().toJson(created));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Datos JSON no válidos");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            Asignatura asignatura = getAsignaturaFromJson(jsonObject);
            Asignatura updated = asignaturaService.update(asignatura);
            writeJsonResponse(response, new Gson().toJson(updated));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Datos JSON no válidos");
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
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
            return;
        }

        try {
            int id = Integer.parseInt(pathParts[1]);
            asignaturaService.delete(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre_asignatura");
        String codigo = request.getParameter("codigo_asignatura");
        String descripcion = request.getParameter("descripcion_asignatura");
        Integer sct = Integer.parseInt(request.getParameter("sct_asignatura"));

        List<Asignatura> asignaturas = asignaturaService.findByFilter(nombre, codigo, descripcion, sct);
        response.getWriter().print(new Gson().toJson(asignaturas));
    }

    private void handleClasesByAsignaturaIdRequest(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathParts[1]);
            String clases = pathParts[2];
            if (!clases.equals("clases")) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Ruta no válida");
                return;
            }
            Asignatura asignatura = asignaturaService.findById(id);
            writeJsonResponse(response, new Gson().toJson(asignatura.getClases()));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private Asignatura getAsignaturaFromJson(JSONObject jsonObject) {
        int id = jsonObject.optInt("asignatura_id", 0);
        String nombre = jsonObject.optString("nombre_asignatura", null);
        String codigo = jsonObject.optString("codigo_asignatura", null);
        String descripcion = jsonObject.optString("descripcion_asignatura", null);
        int sct = jsonObject.optInt("sct_asignatura", 0);

        return new Asignatura(id, nombre, codigo, descripcion, sct, null);
    }
}
