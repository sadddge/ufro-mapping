package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
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
public class AsignaturaController extends BaseController<Asignatura> {

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

    @Override
    protected Asignatura processPost(Asignatura entity) {
        return asignaturaService.add(entity);
    }

    @Override
    protected Asignatura processPut(Asignatura entity) throws EntityNotFoundException {
        return asignaturaService.update(entity);
    }

    @Override
    protected void processDelete(int id) throws EntityNotFoundException {
        asignaturaService.delete(id);
    }

    @Override
    protected Asignatura mapJsonToEntity(JSONObject jsonObject){
        int id = jsonObject.optInt("id", -1);
        String nombre = jsonObject.optString("nombre", null);
        String codigo = jsonObject.optString("codigo", null);
        String descripcion = jsonObject.optString("descripcion", null);
        int sct = jsonObject.optInt("sct", -1);
        return new Asignatura(id, nombre, codigo, descripcion, sct, null);
    }
}
