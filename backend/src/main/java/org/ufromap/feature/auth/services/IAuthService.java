package org.ufromap.feature.auth.services;

import org.ufromap.feature.auth.dto.RegisterRequestDTO;
import org.ufromap.feature.users.dto.UserInfoDTO;
import org.ufromap.feature.users.models.Usuario;

public interface IAuthService {
    String login(String usernameOrEmail, String contrasenia);
    boolean validateSession(String token);
    UserInfoDTO getUserInfo(String token);
    void changePassword(String token, String oldPassword, String newPassword);
    void register(RegisterRequestDTO registerRequestDTO);
}
