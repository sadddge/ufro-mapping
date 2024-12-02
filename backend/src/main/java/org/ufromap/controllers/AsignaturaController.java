package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.ufromap.annotation.*;
import org.ufromap.dto.request.AsignaturaRequestDTO;
import org.ufromap.dto.response.AsignaturaDTO;
import org.ufromap.dto.response.HorarioClaseDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.services.IAsignaturaService;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.impl.AsignaturaServiceImpl;
import org.ufromap.services.impl.HorarioServiceImpl;

import javax.servlet.annotation.WebServlet;
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
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<AsignaturaDTO> asignaturas = asignaturaService.findAll();
        writeJsonResponse(response, asignaturas);
    }

    @GetMapping("/{id}")
    public void findById(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idAsignatura = Integer.parseInt(id);
        AsignaturaDTO asignatura = asignaturaService.findById(idAsignatura);
        writeJsonResponse(response, asignatura);
    }

    @GetMapping("/{id}/horario")
    public void getHorarioByAsignaturaId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idAsignatura = Integer.parseInt(id);
        List<HorarioClaseDTO> asignaturas = horarioService.getHorarioByAsignaturaId(idAsignatura);
        writeJsonResponse(response, asignaturas);
    }

    @PostMapping("")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        AsignaturaRequestDTO asignaturaRequestDTO = mapJsonToEntity(jsonObject);
        AsignaturaDTO asignatura = asignaturaService.add(asignaturaRequestDTO);
        writeJsonResponse(response, asignatura);
    }

    @PutMapping("/{id}")
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        AsignaturaRequestDTO asignaturaRequestDTO = mapJsonToEntity(jsonObject);
        int idAsignatura = Integer.parseInt(id);
        AsignaturaDTO asignatura = asignaturaService.update(idAsignatura, asignaturaRequestDTO);
        writeJsonResponse(response, asignatura);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
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