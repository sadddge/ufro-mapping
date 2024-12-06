package org.ufromap.feature.lectures.controllers;

import org.ufromap.core.annotations.*;
import org.ufromap.core.base.BaseController;
import org.ufromap.feature.lectures.dto.ClaseRequestDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.feature.lectures.services.IClaseService;
import org.ufromap.feature.lectures.services.impl.ClaseServiceImpl;

import java.io.IOException;

@RequestMapping("/api/clases")
public class ClaseController extends BaseController {
    private final IClaseService claseService;
    public ClaseController() {
        this.claseService = new ClaseServiceImpl();
    }

    @GetMapping("")
    @Protected(roles = {"USER", "ADMIN"})
    public void findAll(HttpServletResponse response) throws IOException {
        sendObject(response, claseService.findAll());
    }

    @GetMapping("/{id}")
    @Protected(roles = {"USER", "ADMIN"})
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        sendObject(response, claseService.findById(idInt));
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        ClaseRequestDTO claseRequestDTO = mapJsonToEntity(jsonObject);
        sendObject(response, claseService.add(claseRequestDTO));
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        ClaseRequestDTO claseRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        sendObject(response, claseService.update(idInt, claseRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        claseService.delete(idInt);
        sendObject(response, null);
    }
    protected ClaseRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new ClaseRequestDTO(
                jsonObject.optInt("salaId", -1),
                jsonObject.optInt("asignaturaId", -1),
                jsonObject.optInt("diaSemana", -1),
                jsonObject.optInt("periodo", -1),
                jsonObject.optString("docente", null),
                jsonObject.optInt("modulo", -1)
        );
    }


}