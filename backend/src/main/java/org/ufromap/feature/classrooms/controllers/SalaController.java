package org.ufromap.feature.classrooms.controllers;

import org.json.JSONObject;
import org.ufromap.core.annotations.*;
import org.ufromap.core.base.BaseController;
import org.ufromap.feature.classrooms.dto.SalaRequestDTO;
import org.ufromap.feature.lectures.services.IHorarioService;
import org.ufromap.feature.classrooms.services.ISalaService;
import org.ufromap.feature.lectures.services.impl.HorarioServiceImpl;
import org.ufromap.feature.classrooms.services.impl.SalaServiceImpl;

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
        sendObject(response, salaService.findAll());
    }

    @GetMapping("/{id}")
    @Protected(roles = {"USER", "ADMIN"})
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        sendObject(response, salaService.findById(idInt));
    }

    @GetMapping("/{id}/horario")
    @Protected(roles = {"USER", "ADMIN"})
    public void getHorarioBySalaId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        sendObject(response, horarioService.getHorarioBySalaId(idInt));
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        SalaRequestDTO salaRequestDTO = mapJsonToEntity(jsonObject);
        sendObject(response, salaService.add(salaRequestDTO));
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        SalaRequestDTO salaRequestDTO = mapJsonToEntity(jsonObject);
        int idInt = Integer.parseInt(id);
        sendObject(response, salaService.update(idInt, salaRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN"})
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int idInt = Integer.parseInt(id);
        salaService.delete(idInt);
        sendObject(response, null);
    }

    protected SalaRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new SalaRequestDTO(
                jsonObject.optInt("edificioId", -1),
                jsonObject.optString("nombre", null)
        );
    }

}