package org.ufromap.feature.auth.services.impl;

import org.ufromap.core.utils.JwtUtil;
import org.ufromap.core.utils.PasswordUtil;
import org.ufromap.core.utils.Validator;
import org.ufromap.feature.auth.dto.RegisterRequestDTO;
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
    public String login (String correo, String contrasenia){
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .filter(u -> PasswordUtil.matchesPassword(contrasenia, u.getContrasenia()))
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
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

    @Override
    public void changePassword(String token, String oldPassword, String newPassword) {
        if (!validateSession(token)) {
            throw new BadRequestException("Invalid session");
        }
        Usuario usuario = usuarioRepository.findById(JwtUtil.getUserId(token))
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (PasswordUtil.matchesPassword(oldPassword, usuario.getContrasenia())) {
            usuario.setContrasenia(PasswordUtil.encodePassword(newPassword));
            usuarioRepository.update(usuario);
        } else {
            throw new BadRequestException("Contraseña incorrecta");
        }
    }

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        validateRegisterRequest(registerRequestDTO);
        String passwordHash = PasswordUtil.encodePassword(registerRequestDTO.getContrasenia());
        Usuario usuario = new Usuario(0, "USER", registerRequestDTO.getNombre(), registerRequestDTO.getCorreo(), passwordHash);
        usuarioRepository.add(usuario);
    }

    public void validateRegisterRequest(RegisterRequestDTO registerRequestDTO) {
        Validator.validateEmail(registerRequestDTO.getCorreo());
        Validator.validatePassword(registerRequestDTO.getContrasenia());
        if (usuarioRepository.findByCorreo(registerRequestDTO.getCorreo()).isPresent()) {
            throw new BadRequestException("Correo ya registrado");
        } else if (usuarioRepository.findByNombre(registerRequestDTO.getNombre()).isPresent()) {
            throw new BadRequestException("Nombre de usuario ya registrado");
        }
    }
}
