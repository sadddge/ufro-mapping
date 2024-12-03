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
import org.ufromap.services.AuthService;

@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    private final AuthService usuarioService;

    public AuthController() {
        this.usuarioService = new AuthService();
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = getJson(request);
        String correo = jsonObject.optString("correo", null);
        String contrasenia = jsonObject.optString("contrasenia", null);

        String token = usuarioService.login(correo, contrasenia);
        if (token != null) {
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addHeader("Set-Cookie", cookie.getName() + "=" + cookie.getValue()
                    + "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=" + cookie.getMaxAge());
            response.addCookie(cookie);
            writeJsonResponse(response, "Login successful");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }

    @GetMapping("/validate-session")
    public void validateSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getCookies() == null) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            return;
        }
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token")).findFirst().ifPresent(cookie -> {
            if (usuarioService.validateSession(cookie.getValue())) {
                try {
                    writeJsonResponse(response, "Session is valid");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            }
        });
    }
}
