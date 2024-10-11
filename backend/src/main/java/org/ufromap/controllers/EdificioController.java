package org.ufromap.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ufromap.models.Edificio;
import org.ufromap.services.EdificioService;

import com.google.gson.Gson;

/**
 * Controlador que maneja las solicitudes HTTP relacionadas con los recursos de la entidad {@link Edificio}.
 * Proporciona puntos de acceso a través de la API REST para gestionar operaciones de CRUD (Crear, Leer).
 * Este servlet está mapeado en la ruta `/api/edificio/*`.
 */
@WebServlet("/api/edificio/*")
public class EdificioController extends HttpServlet {
    private final EdificioService edificioService;

    /**
     * Constructor por defecto que inicializa el controlador con una instancia de {@link EdificioService}.
     */
    public EdificioController() {
        this.edificioService = new EdificioService();
    }

    /**
     * Constructor que permite inyectar un servicio de edificios específico.
     *
     * @param edificioService Servicio que se utilizará para gestionar los edificios.
     */
    public EdificioController(EdificioService edificioService) {
        this.edificioService = edificioService;
    }

    /**
     * Maneja las solicitudes GET para obtener uno o varios edificios.
     *
     * - Si no se proporciona un ID, devuelve la lista completa de edificios.
     * - Si se proporciona un ID en la URL, devuelve los detalles del edificio correspondiente.
     *
     * @param request  La solicitud HTTP entrante.
     * @param response La respuesta HTTP que se enviará de vuelta.
     * @throws ServletException Si ocurre un error en la solicitud del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Edificio> edificios = edificioService.getEdificios();
            response.setContentType("application/json");
            response.getWriter().print(new Gson().toJson(edificios));
        } else {
            String id = pathInfo.substring(1);
            try {
                int edificioId = Integer.parseInt(id);
                Edificio edificio = edificioService.getEdificioById(edificioId);
                response.setContentType("application/json");
                response.getWriter().print(new Gson().toJson(edificio));
            } catch (NumberFormatException e) {
                response.setStatus(400);
            }
        }
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo edificio.
     *
     * Lee el cuerpo de la solicitud en formato JSON, convierte los datos a un objeto {@link Edificio},
     * y luego lo agrega a la base de datos a través del servicio.
     *
     * @param request  La solicitud HTTP entrante que contiene los datos del nuevo edificio.
     * @param response La respuesta HTTP que se enviará de vuelta.
     * @throws ServletException Si ocurre un error en la solicitud del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Edificio edificio = new Gson().fromJson(reader, Edificio.class);
        edificioService.addEdificio(edificio);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
