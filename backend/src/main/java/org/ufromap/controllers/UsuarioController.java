package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.ufromap.dto.request.InscripcionRequestDTO;
import org.ufromap.dto.request.UsuarioRequestDTO;
import org.ufromap.dto.response.InscripcionDTO;
import org.ufromap.dto.response.UsuarioDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.IInscripcionService;
import org.ufromap.services.impl.HorarioServiceImpl;
import org.ufromap.services.impl.InscripcionServiceImpl;
import org.ufromap.services.impl.UsuarioServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/usuarios/*")
public class UsuarioController extends CrudController<UsuarioDTO, UsuarioRequestDTO, Usuario> {
    private final IInscripcionService inscripcionService;
    private final IHorarioService horarioService;

    public UsuarioController() {
        super(new UsuarioServiceImpl());
        this.inscripcionService = new InscripcionServiceImpl();
        this.horarioService = new HorarioServiceImpl();
    }

    @Override
    protected UsuarioRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new UsuarioRequestDTO(
                jsonObject.optString("nombre", null),
                jsonObject.optString("correo", null),
                jsonObject.optString("contrasenia", null)
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[0];
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length != 3) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }
        switch (pathParts[2]) {
            case "asignaturas":
                handleGetAsignaturasByUsuarioId(pathParts, response);
                break;
            case "horario":
                handleGetHorario(pathParts, response);
                break;
            default:
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
        }
    }

    @Override
    protected void handleExtraPostRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length != 4 || !pathParts[2].equals("asignaturas")) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }
        handlePostInscripcion(pathParts, response);
    }

    @Override
    protected void handleExtraDeleteRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length != 4 || !pathParts[2].equals("asignaturas")) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }
        handleDeleteInscripcion(pathParts, response);
    }


    private void handleGetHorario(String[] pathParts, HttpServletResponse response) throws IOException {
        try{
            int id = Integer.parseInt(pathParts[1]);
            writeJsonResponse(response, new Gson().toJson(horarioService.getHorarioByUserId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleGetAsignaturasByUsuarioId(String[] pathParts, HttpServletResponse response) throws IOException {
        try{
            int id = Integer.parseInt(pathParts[1]);
            writeJsonResponse(response, new Gson().toJson(inscripcionService.getAsignaturasByUsuarioId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handlePostInscripcion(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(pathParts[1]);
            int asignaturaId = Integer.parseInt(pathParts[3]);
            InscripcionDTO responseDTO = inscripcionService.add(new InscripcionRequestDTO(userId, asignaturaId));
            writeJsonResponse(response, new Gson().toJson(responseDTO));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleDeleteInscripcion(String[] pathParts, HttpServletResponse response) {
        try {
            int userId = Integer.parseInt(pathParts[1]);
            int asignaturaId = Integer.parseInt(pathParts[3]);
            inscripcionService.deleteByUsuarioIdAndAsignaturaId(userId, asignaturaId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}