package org.ufromap.controllers;

import org.ufromap.models.Clase;
import org.ufromap.services.ClaseService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.ufromap.exceptions.BadRequestException;
import org.ufromap.exceptions.EntityNotFoundException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controlador que maneja las solicitudes relacionadas con la entidad Clase.
 * Este controlador se comunica con el servicio {@link ClaseService} para procesar las
 * operaciones relacionadas con las clases.
 */
@WebServlet("/api/clases/*")
public class ClaseController extends BaseController {

    /**
     * Constructor que inicializa el controlador con un servicio de clase.
     * 
     * @param claseService El servicio {@link ClaseService} que se utilizará para manejar las operaciones.
     */
    private final ClaseService claseService;
    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }
    public ClaseController() {
        this.claseService = new ClaseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");
        switch (pathParts.length) {
            case 2:
                handleIdRequest(pathParts[1], response, claseService::findById);
                break;
            case 0:
                if (!params.isEmpty()) {
                    handleQueryRequest(request, response);
                } else {
                    handleAllRequest(response, claseService::findAll);
                }
                break;
            default:
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            Clase clase = getClaseFromJson(jsonObject);
            Clase added = claseService.add(clase);
            writeJsonResponse(response, new Gson().toJson(added));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
        } catch (BadRequestException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            JSONObject jsonObject = getJson(request);
            Clase clase = getClaseFromJson(jsonObject);
            Clase updated = claseService.update(clase);
            writeJsonResponse(response, new Gson().toJson(updated));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON data");
        } catch (EntityNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doDelete (HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");
        if (pathParts.length == 2) {
            try {
                int id = Integer.parseInt(pathParts[1]);
                claseService.delete(id);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NumberFormatException e) {
                sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
            } catch (EntityNotFoundException e) {
                sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
        }
    }

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int salaId;
        int edificioId;
        int asignaturaId;
        String docenteNombre = request.getParameter("docente");
        int diaSemana;
        int periodoClase;
        int modulo;

        try {
            salaId = request.getParameter("sala_id") == null ? 0 : Integer.parseInt(request.getParameter("sala_id"));
            edificioId = request.getParameter("edificio_id") == null ? 0 : Integer.parseInt(request.getParameter("edificio_id"));
            asignaturaId = request.getParameter("asignatura_id") == null ? 0 : Integer.parseInt(request.getParameter("asignatura_id"));
            diaSemana = request.getParameter("dia_semana") == null ? 0 : Integer.parseInt(request.getParameter("dia_semana"));
            periodoClase = request.getParameter("periodo_clase") == null ? 0 : Integer.parseInt(request.getParameter("periodo_clase"));
            modulo = request.getParameter("modulo") == null ? 0 : Integer.parseInt(request.getParameter("modulo"));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        if (salaId != 0 || edificioId != 0 || asignaturaId != 0 || docenteNombre != null || diaSemana != 0 || periodoClase != 0 || modulo != 0) {
            List<Clase> clases = claseService.findByFilter(salaId, edificioId, asignaturaId, diaSemana, periodoClase, docenteNombre, modulo);
            response.getWriter().print(new Gson().toJson(clases));
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }


    }

    private Clase getClaseFromJson(JSONObject jsonObject) {
       int id = jsonObject.optInt("clase_id", 0);
       int salaId = jsonObject.optInt("sala_id", 0);
       int edificioId = jsonObject.optInt("edificio_id", 0);
       int asignaturaId = jsonObject.optInt("asignatura_id", 0);
       int diaSemana = jsonObject.optInt("dia_semana", 0);
       int periodo = jsonObject.optInt("periodo_clase", 0);
       String docente = jsonObject.optString("docente_nombre", null);
       int modulo = jsonObject.optInt("modulo", 0);

       return new Clase(id, salaId, edificioId, asignaturaId, diaSemana, periodo, docente, modulo);
    }


}