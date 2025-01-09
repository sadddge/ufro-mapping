package org.api.ufro_mapping.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.api.ufro_mapping.dto.request.ChangePasswordDTO;
import org.api.ufro_mapping.dto.request.LoginRequestDTO;
import org.api.ufro_mapping.dto.request.UserRegisterDTO;
import org.api.ufro_mapping.jwt.JwtProvider;
import org.api.ufro_mapping.services.IAuthService;
import org.api.ufro_mapping.util.CookieUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IAuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(IAuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login") // Public endpoint
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        String token = authService.login(loginRequestDTO.getUsernameOrEmail(), loginRequestDTO.getPassword());
        CookieUtil.createCookie(response, "AuthToken", token, true, 86400, "localhost");
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        CookieUtil.clearCookie(response, "AuthToken");
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/register") // Public endpoint
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        authService.register(userRegisterDTO);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        String token = jwtProvider.getTokenFromCookie(request);
        Long userId = jwtProvider.getUserId(token);
        authService.changePassword(userId, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/validate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> validateSession(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromCookie(request);
        if (authService.validateSession(token)) {
            return ResponseEntity.ok("Session is valid");
        }
        return ResponseEntity.badRequest().body("Session is invalid");
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getUserInfo(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromCookie(request);
        return authService.getUserInfo(token)
                .map(userInfo -> ResponseEntity.ok(userInfo.toString()))
                .orElse(ResponseEntity.badRequest().body("Invalid token"));
    }
}
