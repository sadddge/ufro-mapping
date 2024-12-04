package org.ufromap.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.ufromap.models.Usuario;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final byte[] SECRET_KEY = "777e1d733ed98d4a5405b421d5778523d241e7fd97d1a94a86e7724a26ffc543".getBytes();
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY);
    private static final long EXPIRATION_TIME = 3600000;

    public static String generateToken(Usuario user) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("role", user.getRol())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static int getUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getUserRole(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
