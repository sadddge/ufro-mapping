package org.ufromap.feature.auth.services;

import org.ufromap.feature.auth.dto.RegisterRequestDTO;
import org.ufromap.feature.users.dto.UserInfoDTO;
import org.ufromap.feature.users.models.Usuario;

public interface IAuthService {
    Usuario validarCredenciales(String correo, String contrasenia);
    String login(String correo, String contrasenia);
    boolean validateSession(String token);
    UserInfoDTO getUserInfo(String token);
    void register(RegisterRequestDTO registerRequestDTO);
}
