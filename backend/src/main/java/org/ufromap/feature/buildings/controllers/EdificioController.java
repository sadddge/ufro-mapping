package org.ufromap.feature.buildings.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.core.annotations.*;
import org.ufromap.core.base.BaseController;
import org.ufromap.feature.buildings.dto.EdificioRequestDTO;
import org.ufromap.feature.buildings.dto.EdificioDTO;
import org.ufromap.feature.buildings.dto.LocationDTO;
import org.ufromap.feature.classrooms.dto.SalaDTO;
import org.ufromap.feature.buildings.services.IEdificioService;
import org.ufromap.feature.classrooms.services.ISalaService;
import org.ufromap.feature.buildings.services.impl.EdificioServiceImpl;
import org.ufromap.feature.classrooms.services.impl.SalaServiceImpl;

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
    @Protected(roles = {"USER", "ADMIN"})
    public void getSalasByEdificioId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        List<SalaDTO> salasDTO = salaService.getSalasByEdificioId(idInt);
        writeJsonResponse(response, salasDTO);
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        EdificioRequestDTO edificioRequestDTO = mapJsonToEntity(jsonObject);
        EdificioDTO edificioDTO = edificioService.add(edificioRequestDTO);
        writeJsonResponse(response, edificioDTO);
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        EdificioRequestDTO edificioRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        EdificioDTO edificioDTO = edificioService.update(idInt, edificioRequestDTO);
        writeJsonResponse(response, edificioDTO);
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN"})
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