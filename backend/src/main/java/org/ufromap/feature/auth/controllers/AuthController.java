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
        addTokenCookie(response, token);
        sendObject(response, "Login successful");
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
            sendObject(response, "Session is valid");
        } else {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
        }
    }

    @GetMapping("/user-info")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie tokenCookie = getTokenCookie(request);
        UserInfoDTO userInfo = authService.getUserInfo(tokenCookie.getValue());
        sendObject(response, userInfo);
    }

    private Cookie getTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new UnauthorizedException("Token cookie not found");
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Token cookie not found"));
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
