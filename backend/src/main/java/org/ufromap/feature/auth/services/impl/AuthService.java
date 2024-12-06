package org.ufromap.feature.auth.services.impl;

import org.ufromap.core.utils.JwtUtil;
import org.ufromap.feature.auth.services.IAuthService;
import org.ufromap.feature.users.dto.UserInfoDTO;
import org.ufromap.core.exceptions.BadRequestException;
import org.ufromap.core.exceptions.EntityNotFoundException;
import org.ufromap.feature.users.models.Usuario;
import org.ufromap.repositories.UsuarioRepository;

public class AuthService implements IAuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthService() {
        this.usuarioRepository = new UsuarioRepository();
    }
    @Override
    public Usuario validarCredenciales(String correo, String contrasenia){
        return usuarioRepository.findByCorreoYContrasenia(correo, contrasenia)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    @Override
    public String login (String correo, String contrasenia){
        Usuario usuario = validarCredenciales(correo, contrasenia);
        return JwtUtil.generateToken(usuario);
    }

    @Override
    public boolean validateSession(String token){
        return JwtUtil.validateToken(token);
    }

    @Override
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
