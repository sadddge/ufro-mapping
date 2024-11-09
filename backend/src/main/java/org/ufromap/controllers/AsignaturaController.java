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

@WebServlet("/api/asignaturas/*")
public class AsignaturaController extends BaseController<Asignatura> {

    public AsignaturaController() {
        super(new AsignaturaService());
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length == 3) {
            handleClasesByAsignaturaIdRequest(pathParts, response);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Ruta no válida");
        }
    }

    private void handleClasesByAsignaturaIdRequest(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathParts[1]);
            String clases = pathParts[2];
            if (!clases.equals("clases")) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Ruta no válida");
                return;
            }
            Asignatura asignatura = service.findById(id);
            writeJsonResponse(response, new Gson().toJson(asignatura.getClases()));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected Asignatura mapJsonToEntity(JSONObject jsonObject) {
        return new Asignatura(
                jsonObject.optInt("id", -1),
                jsonObject.optString("nombre_asignatura", null),
                jsonObject.optString("codigo", null),
                jsonObject.optString("descripcion", null),
                jsonObject.optInt("sct", -1),
                null
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"nombre_asignatura", "codigo", "sct"};
    }
}