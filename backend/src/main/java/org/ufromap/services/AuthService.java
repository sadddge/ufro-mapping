package org.ufromap.services;

import org.ufromap.auth.JwtUtil;
import org.ufromap.exceptions.EntityNotFoundException;
import org.ufromap.models.Usuario;
import org.ufromap.repositories.UsuarioRepository;

public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario validarCredenciales(String correo, String contrasenia){
        Usuario usuario = usuarioRepository.findByCorreoYContrasenia(correo, contrasenia);
        if (usuario == null) {
            throw new EntityNotFoundException("Invalid credentials");
        }
        return usuario;
    }

    public String login (String correo, String contrasenia){
        Usuario usuario = validarCredenciales(correo, contrasenia);
        return JwtUtil.generateToken(usuario.getId());
    }
}
