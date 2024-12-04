package org.ufromap.controllers;

import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import org.ufromap.annotation.GetMapping;
import org.ufromap.annotation.PostMapping;
import org.ufromap.annotation.RequestMapping;
import org.ufromap.dto.response.UserInfoDTO;
import org.ufromap.services.AuthService;

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
        if (token != null) {
            addTokenCookie(response, token);
            writeJsonResponse(response, "Login successful");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = getTokenCookie(request);
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
        Cookie tokenCookie = getTokenCookie(request);
        if (tokenCookie == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            return;
        }
        if (authService.validateSession(tokenCookie.getValue())) {
            writeJsonResponse(response, "Session is valid");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
        }
    }

    @GetMapping("/user-info")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie tokenCookie = getTokenCookie(request);
        if (tokenCookie == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            return;
        }
        UserInfoDTO userInfo = authService.getUserInfo(tokenCookie.getValue());
        writeJsonResponse(response, userInfo);
    }

    private Cookie getTokenCookie(HttpServletRequest request) {
        return request.getCookies() == null ? null : Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    private void addTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue()
                + "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=" + cookie.getMaxAge());
        response.addCookie(cookie);
    }
}
