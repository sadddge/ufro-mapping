package org.ufromap.services;

import org.ufromap.auth.Validator;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.repositories.AsignaturaRepository;
import org.ufromap.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(int id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new EntityNotFoundException("User not found");
        }
        return usuarioRepository.findById(id);
    }

    public Usuario add(Usuario usuario) {
        Validator.validateEmail(usuario.getCorreo());
        Validator.validatePassword(usuario.getContrasenia());
        return usuarioRepository.add(usuario);
    }

    public Usuario update(Usuario usuario) {
        Validator.validateEmail(usuario.getCorreo());
        Validator.validatePassword(usuario.getContrasenia());
        return usuarioRepository.update(usuario);
    }

    public void delete(int id) {
        findById(id);
        usuarioRepository.delete(id);
    }

    //Find by filter (Social Update)
}
