package org.ufromap.feature.courses.controllers;

import org.json.JSONObject;
import org.ufromap.core.annotations.*;
import org.ufromap.core.base.BaseController;
import org.ufromap.feature.courses.dto.AsignaturaRequestDTO;
import org.ufromap.feature.courses.dto.AsignaturaDTO;
import org.ufromap.feature.lectures.dto.HorarioClaseDTO;
import org.ufromap.feature.courses.services.IAsignaturaService;
import org.ufromap.feature.lectures.services.IHorarioService;
import org.ufromap.feature.courses.services.impl.AsignaturaServiceImpl;
import org.ufromap.feature.lectures.services.impl.HorarioServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/asignaturas")
public class AsignaturaController extends BaseController {
    private final IAsignaturaService asignaturaService;
    private final IHorarioService horarioService;
    public AsignaturaController() {
        this.asignaturaService = new AsignaturaServiceImpl();
        this.horarioService = new HorarioServiceImpl();
    }

    @GetMapping("")
    @Protected(roles = {"USER", "ADMIN"})
    public void findAll(HttpServletResponse response) throws IOException {
        List<AsignaturaDTO> asignaturas = asignaturaService.findAll();
        writeJsonResponse(response, asignaturas);
    }

    @GetMapping("/{id}")
    @Protected(roles = {"USER", "ADMIN"})
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idAsignatura = Integer.parseInt(id);
        AsignaturaDTO asignatura = asignaturaService.findById(idAsignatura);
        writeJsonResponse(response, asignatura);
    }

    @GetMapping("/{id}/horario")
    @Protected(roles = {"USER", "ADMIN"})
    public void getHorarioByAsignaturaId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idAsignatura = Integer.parseInt(id);
        List<HorarioClaseDTO> asignaturas = horarioService.getHorarioByAsignaturaId(idAsignatura);
        writeJsonResponse(response, asignaturas);
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        AsignaturaRequestDTO asignaturaRequestDTO = mapJsonToEntity(jsonObject);
        AsignaturaDTO asignatura = asignaturaService.add(asignaturaRequestDTO);
        writeJsonResponse(response, asignatura);
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        AsignaturaRequestDTO asignaturaRequestDTO = mapJsonToEntity(jsonObject);
        int idAsignatura = Integer.parseInt(id);
        AsignaturaDTO asignatura = asignaturaService.update(idAsignatura, asignaturaRequestDTO);
        writeJsonResponse(response, asignatura);
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idAsignatura = Integer.parseInt(id);
        asignaturaService.delete(idAsignatura);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    protected AsignaturaRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new AsignaturaRequestDTO(
                jsonObject.optString("nombre", null),
                jsonObject.optString("codigo", null),
                jsonObject.optString("descripcion", null),
                jsonObject.optIntegerObject("sct", null)
        );
    }
}