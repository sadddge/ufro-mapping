package org.ufromap.controllers;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

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
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", token);
            writeJsonResponse(response, jsonResponse.toString());
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }
}
