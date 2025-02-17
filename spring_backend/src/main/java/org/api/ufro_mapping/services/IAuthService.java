package org.api.ufro_mapping.services;

import org.api.ufro_mapping.dto.request.UserRegisterDTO;
import org.api.ufro_mapping.dto.response.GeneralUserInfoDTO;

import java.util.Optional;

public interface IAuthService {
    String login(String usernameOrEmail, String password);
    boolean validateSession(String token);
    Optional<GeneralUserInfoDTO> getUserInfo(String token);
    void register(UserRegisterDTO userRegisterDTO);
    void changePassword(Long id, String oldPassword, String newPassword);
}
