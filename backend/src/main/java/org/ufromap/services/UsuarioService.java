package org.ufromap.services;

import org.json.JSONObject;
import org.ufromap.auth.Validator;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Asignatura;
import org.ufromap.models.Clase;
import org.ufromap.models.Inscripcion;
import org.ufromap.models.Usuario;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.InscripcionRepository;
import org.ufromap.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioService implements IService<Usuario> {
    private final UsuarioRepository usuarioRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final InscripcionRepository inscripcionRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
        this.inscripcionRepository = new InscripcionRepository();
        this.asignaturaRepository = new AsignaturaRepository();
    }

    public UsuarioService(UsuarioRepository usuarioRepository, InscripcionRepository inscripcionRepository, AsignaturaRepository asignaturaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(int id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("User not found");
        }
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findByFilter(Map<String, Object> filters) throws EntityNotFoundException {
        return List.of();
    }

    @Override
    public Usuario add(Usuario usuario) {
        Validator.validateEmail(usuario.getCorreo());
        Validator.validatePassword(usuario.getContrasenia());
        return usuarioRepository.add(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        Validator.validateEmail(usuario.getCorreo());
        Validator.validatePassword(usuario.getContrasenia());
        return usuarioRepository.update(usuario);
    }

    @Override
    public Usuario patch(Usuario entity, JSONObject jsonObject) throws EntityNotFoundException {
        Usuario usuario = new Usuario();
        String nombre = jsonObject.optString("nombre", entity.getNombre());
        String correo = jsonObject.optString("correo", entity.getCorreo());
        String contrasenia = jsonObject.optString("contrasenia", entity.getContrasenia());
        usuario.setId(entity.getId());
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContrasenia(contrasenia);
        return update(usuario);
    }

    @Override
    public void delete(int id) {
        findById(id);
        usuarioRepository.delete(id);
    }

    public List<Clase> getClasesByUsuarioId(int id) {
        List<Clase> clases = new ArrayList<>();
        Usuario usuario = findById(id);

        for (Asignatura asignatura : usuario.getAsignaturas()) {
            clases.addAll(asignatura.getClases());
        }
        return clases;
    }

    public List<Asignatura> getAsignaturasByUsuarioId(int id) {
        List<Inscripcion> inscripciones = getInscripcionesByUsuarioId(id);
        return asignaturaRepository.findByInscripciones(inscripciones);
    }

    public List<Inscripcion> getInscripcionesByUsuarioId(int id) {
        return inscripcionRepository.findByUsuarioId(id);
    }

    public Inscripcion inscribirAsignatura(Inscripcion inscripcion) {
        return inscripcionRepository.add(inscripcion);
    }

    public void deleteInscripcion(int id, int idAsignatura) {
        inscripcionRepository.deleteInscripcionByUsuarioIdAndAsignaturaId(id, idAsignatura);
    }
}
