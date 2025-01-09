package org.ufromap.core.utils;

import org.ufromap.core.exceptions.UnauthorizedException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieUtil {

    public static Cookie getTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new UnauthorizedException("Token cookie not found");
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Token cookie not found"));
    }

    public static void addTokenCookie(HttpServletResponse response, String token) {
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
