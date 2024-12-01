package org.ufromap.controllers;

import org.json.JSONObject;
import org.ufromap.dto.request.SalaRequestDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.impl.HorarioServiceImpl;
import org.ufromap.services.impl.SalaServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/salas/*")
public class SalaController extends CrudController<SalaDTO, SalaRequestDTO, Sala> {
    private final IHorarioService horarioService;
    public SalaController() {
        super(new SalaServiceImpl());
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
            writeJsonResponse(response, gson.toJson(horarioService.getHorarioBySalaId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
    @Override
    protected SalaRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new SalaRequestDTO(
                jsonObject.optInt("edificioId", -1),
                jsonObject.optString("nombre", null)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[]{"edificioId", "nombre"};
    }
}