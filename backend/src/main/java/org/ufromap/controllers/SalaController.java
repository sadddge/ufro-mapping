package org.ufromap.controllers;

import org.json.JSONObject;
import org.ufromap.annotation.*;
import org.ufromap.dto.request.SalaRequestDTO;
import org.ufromap.dto.response.SalaDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Sala;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.ISalaService;
import org.ufromap.services.impl.HorarioServiceImpl;
import org.ufromap.services.impl.SalaServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/api/salas")
public class SalaController extends BaseController {
    private final ISalaService salaService;
    private final IHorarioService horarioService;
    public SalaController() {
        this.salaService = new SalaServiceImpl();
        this.horarioService = new HorarioServiceImpl();
    }

    @GetMapping("")
    @Protected(roles = {"USER", "ADMIN"})
    public void findAll(HttpServletResponse response) throws IOException {
        writeJsonResponse(response, salaService.findAll());
    }

    @GetMapping("/{id}")
    @Protected(roles = {"USER", "ADMIN"})
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, salaService.findById(idInt));
    }

    @GetMapping("/{id}/horario")
    @Protected(roles = {"USER", "ADMIN"})
    public void getHorarioBySalaId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, horarioService.getHorarioBySalaId(idInt));
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        SalaRequestDTO salaRequestDTO = mapJsonToEntity(jsonObject);
        writeJsonResponse(response, salaService.add(salaRequestDTO));
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        SalaRequestDTO salaRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        writeJsonResponse(response, salaService.update(idInt, salaRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        salaService.delete(idInt);
        writeJsonResponse(response, null);
    }

    protected SalaRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new SalaRequestDTO(
                jsonObject.optInt("edificioId", -1),
                jsonObject.optString("nombre", null)
        );
    }

}