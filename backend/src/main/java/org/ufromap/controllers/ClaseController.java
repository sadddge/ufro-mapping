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
public class ClaseController extends BaseController<Clase> {

    private final ClaseService claseService;
    /**
     * Constructor que inicializa el controlador con un servicio de clase.
     *
     * @param claseService El servicio {@link ClaseService} que se utilizará para manejar las operaciones.
     */
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

    private void handleQueryRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer salaId;
        Integer edificioId;
        Integer asignaturaId;
        String docenteNombre = request.getParameter("docente");
        Integer diaSemana;
        Integer periodoClase;
        Integer modulo;

        try {
            salaId = request.getParameter("sala_id") == null ? null : Integer.parseInt(request.getParameter("sala_id"));
            edificioId = request.getParameter("edificio_id") == null ? null : Integer.parseInt(request.getParameter("edificio_id"));
            asignaturaId = request.getParameter("asignatura_id") == null ? null : Integer.parseInt(request.getParameter("asignatura_id"));
            diaSemana = request.getParameter("dia_semana") == null ? null : Integer.parseInt(request.getParameter("dia_semana"));
            periodoClase = request.getParameter("periodo_clase") == null ? null : Integer.parseInt(request.getParameter("periodo_clase"));
            modulo = request.getParameter("modulo") == null ? null : Integer.parseInt(request.getParameter("modulo"));
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        if (salaId != null || edificioId != null || asignaturaId != null || docenteNombre != null || diaSemana != null || periodoClase != null || modulo != null) {
            List<Clase> clases = claseService.findByFilter(salaId, edificioId, asignaturaId, diaSemana, periodoClase, docenteNombre, modulo);
            response.getWriter().print(new Gson().toJson(clases));
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }


    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePost(request, response);
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlePut(request, response);
    }

    @Override
    protected void doDelete (HttpServletRequest request, HttpServletResponse response) {
        handleDelete(request, response);
    }




    @Override
    protected Clase processPost(Clase entity) throws BadRequestException {
        return claseService.add(entity);
    }

    @Override
    protected Clase processPut(Clase entity) throws EntityNotFoundException {
        return claseService.update(entity);
    }

    @Override
    protected void processDelete(int id) throws EntityNotFoundException {
        claseService.delete(id);
    }

    @Override
    protected Clase mapJsonToEntity(JSONObject jsonObject) {
        return new Clase(
            jsonObject.optInt("id", -1),
            jsonObject.optInt("sala_id", -1),
            jsonObject.optInt("edificio_id", -1),
            jsonObject.optInt("asignatura_id", -1),
            jsonObject.optInt("dia_semana", -1),
                jsonObject.optInt("periodo_clase", -1),
                jsonObject.optString("docente", null),
                jsonObject.optInt("modulo", -1)
        );
    }
}