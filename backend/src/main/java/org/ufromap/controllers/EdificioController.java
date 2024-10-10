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

@WebServlet("/api/edificio/*")
public class EdificioController extends HttpServlet {
    private final EdificioService edificioService;

    public EdificioController() {
        this.edificioService = new EdificioService();
    }

    public EdificioController(EdificioService edificioService) {
        this.edificioService = edificioService;
    }

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader  reader = request.getReader();
        Edificio edificio = new Gson().fromJson(reader, Edificio.class);
        edificioService.addEdificio(edificio);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
    
}
