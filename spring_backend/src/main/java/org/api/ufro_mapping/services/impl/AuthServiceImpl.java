package org.api.ufro_mapping.services.impl;
import lombok.extern.java.Log;
import org.api.ufro_mapping.dto.request.UserRegisterDTO;
import org.api.ufro_mapping.dto.response.GeneralUserInfoDTO;
import org.api.ufro_mapping.jwt.JwtProvider;
import org.api.ufro_mapping.models.Role;
import org.api.ufro_mapping.models.User;
import org.api.ufro_mapping.repositories.UserRepository;
import org.api.ufro_mapping.services.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Log
@Service
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    public AuthServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtProvider.generateToken(user);
        }
        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public boolean validateSession(String token) {
        return jwtProvider.validateToken(token);
    }

    @Override
    public Optional<GeneralUserInfoDTO> getUserInfo(String token) {
        if (!validateSession(token)) {
            return Optional.empty();
        }
        return Optional.of(GeneralUserInfoDTO.builder()
                .id(jwtProvider.getUserId(token))
                .role(jwtProvider.getUserRole(token))
                .build());
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        } else if (userRepository.existsByName(userRegisterDTO.getName())) {
            throw new RuntimeException("Name already in use");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        User user = new User();
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(new Role("USER", null));
        userRepository.save(user);
    }
}
