package org.ufromap.controllers;

import org.ufromap.models.Asignatura;
import org.ufromap.services.AsignaturaService;
import org.ufromap.services.EdificioService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Controlador que maneja las solicitudes relacionadas con la entidad "Asignatura".
 * 
 */
@WebServlet("/api/asignaturas/*")
public class AsignaturaController extends BaseController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController() {
        this.asignaturaService = new AsignaturaService();
    }

    /**
     * Constructor que inicializa el controlador con el servicio de Asignatura.
     * @param asignaturaService el servicio que maneja la lógica de negocio relacionada con Asignatura.
     */
    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo == null ? new String[0] : pathInfo.trim().split("/");

        System.out.println(pathInfo + " ola");
    }

}