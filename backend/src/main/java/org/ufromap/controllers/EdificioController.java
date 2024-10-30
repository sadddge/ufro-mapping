package org.ufromap.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
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
     * Maneja las solicitudes GET para obtener información sobre los edificios.
     * Si la solicitud contiene un ID de edificio, se devolverá la información detallada de ese edificio.
     * De lo contrario, se devolverá una lista de todos los edificios registrados en la base de datos.
     *
     * @param request  La solicitud HTTP entrante que contiene los parámetros de filtrado.
     * @param response La respuesta HTTP que se enviará de vuelta.
     * @throws ServletException Si ocurre un error en la solicitud del servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        String nombre = request.getParameter("nombre");
        String alias = request.getParameter("alias");
        String tipo = request.getParameter("tipo");
        Float latitud = request.getParameter("latitud") == null ? null : Float.parseFloat(request.getParameter("latitud"));
        Float longitud = request.getParameter("longitud") == null ? null : Float.parseFloat(request.getParameter("longitud"));

        if (nombre != null || alias != null || tipo != null || latitud != null || longitud != null) {
            List<Edificio> edificios = edificioService.findByFilter(nombre, alias, tipo, latitud, longitud);
            response.getWriter().print(new Gson().toJson(edificios));
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Edificio> edificios = edificioService.findAll();
            response.getWriter().print(new Gson().toJson(edificios));
            return;
        }

        String id = pathInfo.substring(1);

        try {
            int edificioId = Integer.parseInt(id);
            Edificio edificio = edificioService.findById(edificioId);
            if (edificio == null) {
                response.setStatus(404);
                return;
            }
            response.getWriter().print(new Gson().toJson(edificio));
        } catch (NumberFormatException e) {
            response.setStatus(400);
        }
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo edificio.
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
        response.setContentType("application/json");

        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String json = sb.toString();
        JSONObject jsonObject = new JSONObject(json);

        String nombre = jsonObject.getString("nombre_edificio");
        String alias = jsonObject.optString("alias_edificio", "");
        String tipo = jsonObject.optString("tipo_edifico", "");
        String latitud = jsonObject.getString("latitud");
        String longitud = jsonObject.getString("longitud");


        alias = alias == null ? "" : alias;
        tipo = tipo == null ? "" : tipo;

        if (nombre == null || latitud == null || longitud == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        Edificio edificio = new Edificio(0, nombre, alias, tipo, Float.parseFloat(latitud), Float.parseFloat(longitud), null);
        edificioService.add(edificio);
        response.getWriter().print(new Gson().toJson(edificio));
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
