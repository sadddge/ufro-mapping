package org.ufromap.controllers;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import org.ufromap.services.AuthService;

@WebServlet("/auth/login")
public class AuthController extends HttpServlet {

    private final AuthService usuarioService;

    public AuthController() {
        this.usuarioService = new AuthService();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(body.toString());
        String correo = jsonObject.optString("correo", null);
        String contrasenia = jsonObject.optString("contrasenia", null);

        String token = usuarioService.login(correo, contrasenia);
        if (token != null) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("token", token);
            response.getWriter().write(jsonResponse.toString());

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("error", "Invalid credentials");
            response.getWriter().write(jsonResponse.toString());
        }
    }

}
