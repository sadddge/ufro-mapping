package org.ufromap.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.annotation.*;
import org.ufromap.dto.request.EdificioRequestDTO;
import org.ufromap.dto.response.EdificioDTO;
import org.ufromap.dto.response.LocationDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Edificio;
import org.ufromap.services.IEdificioService;
import org.ufromap.services.ISalaService;
import org.ufromap.services.impl.EdificioServiceImpl;
import org.ufromap.services.impl.SalaServiceImpl;

@RequestMapping("/api/edificios")
public class EdificioController extends BaseController {
    private final IEdificioService edificioService;
    private final ISalaService salaService;

    public EdificioController() {
        this.edificioService = new EdificioServiceImpl();
        this.salaService = new SalaServiceImpl();
    }

    @GetMapping("")
    public void findAll(HttpServletResponse response) throws IOException {
        writeJsonResponse(response, edificioService.findAll());
    }

    @GetMapping("/{id}")
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, edificioService.findById(idInt));
    }

    @GetMapping("/locations")
    public void findAllLocations(HttpServletResponse response) throws IOException {
        List<LocationDTO> locations = edificioService.findAllLocations();
        writeJsonResponse(response, locations);
    }

    @GetMapping("/{id}/salas")
    public void getSalasByEdificioId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        List<SalaDTO> salasDTO = salaService.getSalasByEdificioId(idInt);
        writeJsonResponse(response, salasDTO);
    }

    @PostMapping("")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        EdificioRequestDTO edificioRequestDTO = mapJsonToEntity(jsonObject);
        EdificioDTO edificioDTO = edificioService.add(edificioRequestDTO);
        writeJsonResponse(response, edificioDTO);
    }

    @PutMapping("/{id}")
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        EdificioRequestDTO edificioRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        EdificioDTO edificioDTO = edificioService.update(idInt, edificioRequestDTO);
        writeJsonResponse(response, edificioDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        edificioService.delete(idInt);
        writeJsonResponse(response, null);
    }

    protected EdificioRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new EdificioRequestDTO(
                jsonObject.optString("nombre", null),
                jsonObject.optString("alias", null),
                jsonObject.optString("tipo", null),
                jsonObject.optFloat("latitud", -1),
                jsonObject.optFloat("longitud", -1)
        );
    }

}