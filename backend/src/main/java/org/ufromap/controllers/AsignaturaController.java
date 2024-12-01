package org.ufromap.controllers;

import org.json.JSONObject;
import org.ufromap.dto.request.AsignaturaRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.impl.AsignaturaServiceImpl;
import org.ufromap.services.impl.HorarioServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/asignaturas/*")
public class AsignaturaController extends CrudController<AsignaturaDTO, AsignaturaRequestDTO, Asignatura> {

    private final IHorarioService horarioService;
    public AsignaturaController() {
        super(new AsignaturaServiceImpl());
        this.horarioService = new HorarioServiceImpl();
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length != 3) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }
        if (pathParts[2].equals("horario")) {
            handleGetHorario(pathParts, response);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleGetHorario(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathParts[1]);
            writeJsonResponse(response, gson.toJson(horarioService.getHorarioByAsignaturaId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected AsignaturaRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new AsignaturaRequestDTO(
                jsonObject.optString("nombre", null),
                jsonObject.optString("codigo", null),
                jsonObject.optString("descripcion", null),
                jsonObject.optIntegerObject("sct", null)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"nombre", "codigo", "sct"};
    }
}