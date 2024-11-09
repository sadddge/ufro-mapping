package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.services.IService;
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
        this.usuarioService = (UsuarioService) this.service;
    }

    @Override
    protected Usuario mapJsonToEntity(JSONObject jsonObject) {
        return new Usuario(
                jsonObject.optInt("id",-1),
                jsonObject.optString("nombre_usuario", null),
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
        switch (pathParts.length) {
            case 3:
                handleGetClases(pathParts, response);
                break;
            case 1:
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
}