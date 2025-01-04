package org.ufromap.feature.auth.controllers;

import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import org.ufromap.core.annotations.GetMapping;
import org.ufromap.core.annotations.PostMapping;
import org.ufromap.core.annotations.RequestMapping;
import org.ufromap.core.base.BaseController;
import org.ufromap.core.exceptions.UnauthorizedException;
import org.ufromap.core.utils.CookieUtil;
import org.ufromap.feature.auth.dto.RegisterRequestDTO;
import org.ufromap.feature.users.dto.UserInfoDTO;
import org.ufromap.feature.auth.services.impl.AuthService;

@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        String correo = jsonObject.optString("correo", null);
        String contrasenia = jsonObject.optString("contrasenia", null);
        String token = authService.login(correo, contrasenia);
        CookieUtil.addTokenCookie(response, token);
        sendMessage(response, "Login successful");
    }

    @PostMapping("/register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                .nombre(jsonObject.optString("nombre", null))
                .correo(jsonObject.optString("correo", null))
                .contrasenia(jsonObject.optString("contrasenia", null))
                .build();
        authService.register(registerRequestDTO);
        sendMessage(response, "Registration successful");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = CookieUtil.getTokenCookie(request);
        if (tokenCookie != null) {
            System.out.println("Removing token cookie");
            System.out.println(tokenCookie.getName());
            System.out.println(tokenCookie.getValue());
            tokenCookie.setValue("");
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(0);
            response.addCookie(tokenCookie);
        }
    }

    @GetMapping("/validate-session")
    public void validateSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie tokenCookie = CookieUtil.getTokenCookie(request);
        if (tokenCookie == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            return;
        }
        if (authService.validateSession(tokenCookie.getValue())) {
            sendMessage(response, "Session is valid");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
        }
    }

    @GetMapping("/user-info")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie tokenCookie = CookieUtil.getTokenCookie(request);
        UserInfoDTO userInfo = authService.getUserInfo(tokenCookie.getValue());
        sendObject(response, userInfo);
    }


}
