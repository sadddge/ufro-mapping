package org.ufromap.controllers;

import org.ufromap.annotation.*;
import org.ufromap.dto.request.ClaseRequestDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.ufromap.services.IClaseService;
import org.ufromap.services.impl.ClaseServiceImpl;

import java.io.IOException;

@RequestMapping("/api/clases")
public class ClaseController extends BaseController {
    private final IClaseService claseService;
    public ClaseController() {
        this.claseService = new ClaseServiceImpl();
    }

    @GetMapping("")
    public void findAll(HttpServletResponse response) throws IOException {
        writeJsonResponse(response, claseService.findAll());
    }

    @GetMapping("/{id}")
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, claseService.findById(idInt));
    }

    @PostMapping("")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        ClaseRequestDTO claseRequestDTO = mapJsonToEntity(jsonObject);
        writeJsonResponse(response, claseService.add(claseRequestDTO));
    }

    @PutMapping("/{id}")
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        ClaseRequestDTO claseRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, claseService.update(idInt, claseRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        claseService.delete(idInt);
        writeJsonResponse(response, null);
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