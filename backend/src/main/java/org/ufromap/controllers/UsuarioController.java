package org.ufromap.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Inscripcion;
import org.ufromap.models.Usuario;
import org.ufromap.services.UsuarioService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/usuarios/*")
public class UsuarioController extends BaseController<Usuario> {
    private final UsuarioService usuarioService;

    public UsuarioController() {
        super(new UsuarioService());
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.usuarioService = (UsuarioService) this.service;
    }

    @Override
    protected Usuario mapJsonToEntity(JSONObject jsonObject) {
        return new Usuario(
                jsonObject.optInt("id",-1),
                jsonObject.optString("nombre", null),
                jsonObject.optString("correo", null),
                jsonObject.optString("contrasenia", null),
                null
        );
    }

    @Override
    protected String[] getValidFilters() {
        return new String[0];
    }

    @Override
    protected void handleExtraGetRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length == 3) {
            switch (pathParts[2]) {
                case "clases":
                    handleGetClases(pathParts, response);
                    break;
                case "asignaturas":
                    handleGetAsignaturas(pathParts, response);
                    break;
                default:
                    sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
            }
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    @Override
    protected void handleExtraPostRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (pathParts.length == 4) {
            handleInscribirAsignatura(pathParts, response);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    @Override
    protected void handleExtraDeleteRequests(String[] pathParts, HttpServletRequest request, HttpServletResponse response) {
        if (pathParts.length == 4) {
            handleDeleteInscripcion(pathParts, response);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }



    private void handleGetClases(String[] pathParts, HttpServletResponse response) throws IOException {
        try{
            int id = Integer.parseInt(pathParts[1]);
            String clases = pathParts[2];
            if (!clases.equals("clases")){
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
                return;
            }
            writeJsonResponse(response, new Gson().toJson(usuarioService.getClasesByUsuarioId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleGetAsignaturas(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(pathParts[1]);
            String asignaturas = pathParts[2];
            if (!asignaturas.equals("asignaturas")) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, "Invalid path");
                return;
            }
            writeJsonResponse(response, new Gson().toJson(usuarioService.getAsignaturasByUsuarioId(id)));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleInscribirAsignatura(String[] pathParts, HttpServletResponse response) throws IOException {
        try {
            int[] ids = parseAndValidateIds(pathParts, response);
            if (ids == null) return;

            Inscripcion inscripcion = usuarioService.inscribirAsignatura(new Inscripcion(-1, ids[0], ids[1]));

            if (inscripcion == null) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid inscripcion");
                return;
            }
            writeJsonResponse(response, new Gson().toJson(inscripcion));
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleDeleteInscripcion(String[] pathParts, HttpServletResponse response) {
        try {
            int[] ids = parseAndValidateIds(pathParts, response);
            if (ids == null) return;

            usuarioService.deleteInscripcion(ids[0], ids[1]);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private int[] parseAndValidateIds(String[] pathParts, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(pathParts[1]);
            String action = pathParts[2];
            int idAsignatura = Integer.parseInt(pathParts[3]);

            if (!"inscribir".equals(action)) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return null;
            }
            return new int[] { idUsuario, idAsignatura };
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
            return null;
        }
    }

}