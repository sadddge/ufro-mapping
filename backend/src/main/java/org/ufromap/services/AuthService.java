package org.ufromap.services;

import org.ufromap.auth.JwtUtil;
import org.ufromap.dto.response.UserInfoDTO;
import org.ufromap.exceptions.BadRequestException;
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
        return usuarioRepository.findByCorreoYContrasenia(correo, contrasenia)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    public String login (String correo, String contrasenia){
        Usuario usuario = validarCredenciales(correo, contrasenia);
        return JwtUtil.generateToken(usuario);
    }

    public boolean validateSession(String token){
        return JwtUtil.validateToken(token);
    }

    public UserInfoDTO getUserInfo(String value) {
        if (!validateSession(value)) {
            throw new BadRequestException("Invalid session");
        }
        return UserInfoDTO.builder()
                .id(JwtUtil.getUserId(value))
                .rol(JwtUtil.getUserRole(value))
                .build();
    }
}
