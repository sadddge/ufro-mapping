package org.ufromap.feature.users.controllers;

import org.json.JSONObject;
import org.ufromap.core.annotations.*;
import org.ufromap.core.base.BaseController;
import org.ufromap.feature.users.dto.InscripcionRequestDTO;
import org.ufromap.feature.users.dto.UsuarioRequestDTO;
import org.ufromap.feature.users.dto.InscripcionDTO;
import org.ufromap.feature.users.dto.UsuarioDTO;
import org.ufromap.feature.lectures.services.IHorarioService;
import org.ufromap.feature.users.services.IInscripcionService;
import org.ufromap.feature.users.services.IUsuarioService;
import org.ufromap.feature.lectures.services.impl.HorarioServiceImpl;
import org.ufromap.feature.users.services.impl.InscripcionServiceImpl;
import org.ufromap.feature.users.services.impl.UsuarioServiceImpl;

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
    @Protected(roles = {"ADMIN"})
    public void findAll(HttpServletResponse response) throws IOException {
        sendObject(response, usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @Protected(roles = {"USER", "ADMIN"})
    public void findById(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        UsuarioDTO usuario = usuarioService.findById(userId);
        sendObject(response, usuario);
    }

    @GetMapping("/{id}/asignaturas")
    @Protected(roles = {"USER", "ADMIN"})
    public void getAsignaturasByUsuarioId(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        sendObject(response, inscripcionService.getAsignaturasByUsuarioId(userId));
    }

    @GetMapping("/{id}/horario")
    @Protected(roles = {"USER", "ADMIN"})
    public void getHorario(@PathParam("id") String id, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        sendObject(response, horarioService.getHorarioByUserId(userId));
    }

    @PostMapping("")
    @Protected(roles = {"ADMIN"})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        UsuarioRequestDTO usuarioRequestDTO = mapJsonToEntity(jsonObject);
        UsuarioDTO usuario = usuarioService.add(usuarioRequestDTO);
        sendObject(response, usuario);
    }

    @PostMapping("/{id}/asignaturas/{asignaturaId}")
    @Protected(roles = {"USER", "ADMIN"})
    public void addInscripcion(@PathParam("id") String id, @PathParam("asignaturaId") String asignaturaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        int asignaturaIdInt = Integer.parseInt(asignaturaId);
        if (usuarioService.validateUser(request, userId)) {
            InscripcionRequestDTO inscripcionRequestDTO = new InscripcionRequestDTO(userId, asignaturaIdInt);
            InscripcionDTO inscripcion = inscripcionService.add(inscripcionRequestDTO);
            sendObject(response, inscripcion);
        } else {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Ya inscrito en la asignatura");
        }
    }

    @PutMapping("/{id}")
    @Protected(roles = {"ADMIN", "USER"})
    public void update(@PathParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        UsuarioRequestDTO usuarioRequestDTO = mapJsonToEntity(jsonObject);
        int userId = Integer.parseInt(id);
        if (usuarioService.validateUser(request, userId)) {
            UsuarioDTO usuario = usuarioService.update(userId, usuarioRequestDTO);
            sendObject(response, usuario);
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
        }
    }

    @DeleteMapping("/{id}")
    @Protected(roles = {"ADMIN", "USER"})
    public void delete(@PathParam("id") String id, HttpServletRequest req, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        if (usuarioService.validateUser(req, userId)) {
            usuarioService.delete(userId);
            sendMessage(response, "Usuario eliminado");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
        }
    }

    @DeleteMapping("/{id}/asignaturas/{asignaturaId}")
    @Protected(roles = {"USER", "ADMIN"})
    public void deleteInscripcion(@PathParam("id") String id, @PathParam("asignaturaId") String asignaturaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(id);
        int asignaturaIdInt = Integer.parseInt(asignaturaId);
        if (usuarioService.validateUser(request, userId)) {
            inscripcionService.deleteByUsuarioIdAndAsignaturaId(userId, asignaturaIdInt);
            sendMessage(response, "Inscripcion eliminada");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
        }
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