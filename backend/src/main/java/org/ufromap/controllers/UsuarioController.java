package org.ufromap.controllers;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.ufromap.annotation.*;
import org.ufromap.dto.request.InscripcionRequestDTO;
import org.ufromap.dto.request.UsuarioRequestDTO;
import org.ufromap.dto.response.InscripcionDTO;
import org.ufromap.dto.response.UsuarioDTO;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.services.IHorarioService;
import org.ufromap.services.IInscripcionService;
import org.ufromap.services.IUsuarioService;
import org.ufromap.services.impl.HorarioServiceImpl;
import org.ufromap.services.impl.InscripcionServiceImpl;
import org.ufromap.services.impl.UsuarioServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/api/usuarios")
public class UsuarioController extends BaseController {
    private final IUsuarioService usuarioService;
    private final IInscripcionService inscripcionService;
    private final IHorarioService horarioService;

    public UsuarioController() {
        this.inscripcionService = new InscripcionServiceImpl();
        this.horarioService = new HorarioServiceImpl();
        this.usuarioService = new UsuarioServiceImpl();
    }

    @GetMapping("")
    public void findAll(HttpServletResponse response) throws IOException {
        writeJsonResponse(response, usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        UsuarioDTO usuario = usuarioService.findById(userId);
        writeJsonResponse(response, usuario);
    }

    @GetMapping("/{id}/asignaturas")
    public void getAsignaturasByUsuarioId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        writeJsonResponse(response, inscripcionService.getAsignaturasByUsuarioId(userId));
    }

    @GetMapping("/{id}/horario")
    public void getHorario(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        writeJsonResponse(response, horarioService.getHorarioByUserId(userId));
    }

    @PostMapping("")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        UsuarioRequestDTO usuarioRequestDTO = mapJsonToEntity(jsonObject);
        UsuarioDTO usuario = usuarioService.add(usuarioRequestDTO);
        writeJsonResponse(response, usuario);
    }

    @PostMapping("/{id}/asignaturas/{asignaturaId}")
    public void addInscripcion(@PathParam("id") String id, @PathParam("asignaturaId") String asignaturaId, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        int asignaturaIdInt = Integer.parseInt(asignaturaId);
        InscripcionDTO responseDTO = inscripcionService.add(new InscripcionRequestDTO(userId, asignaturaIdInt));
        writeJsonResponse(response, responseDTO);
    }

    @PutMapping("/{id}")
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        UsuarioRequestDTO usuarioRequestDTO = mapJsonToEntity(jsonObject);
        int userId = Integer.parseInt(id);
        UsuarioDTO usuario = usuarioService.update(userId, usuarioRequestDTO);
        writeJsonResponse(response, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        usuarioService.delete(userId);
        writeJsonResponse(response, null);
    }

    @DeleteMapping("/{id}/asignaturas/{asignaturaId}")
    public void deleteInscripcion(@PathParam("id") String id, @PathParam("asignaturaId") String asignaturaId, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        int asignaturaIdInt = Integer.parseInt(asignaturaId);
        inscripcionService.deleteByUsuarioIdAndAsignaturaId(userId, asignaturaIdInt);
        writeJsonResponse(response, null);
    }


    protected UsuarioRequestDTO mapJsonToEntity(JSONObject jsonObject) {
        return new UsuarioRequestDTO(
                jsonObject.optString("rol", null),
                jsonObject.optString("nombre", null),
                jsonObject.optString("correo", null),
                jsonObject.optString("contrasenia", null)
        );
    }
}