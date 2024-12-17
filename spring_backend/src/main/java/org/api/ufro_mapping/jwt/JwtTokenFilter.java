package org.api.ufro_mapping.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Log
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public JwtTokenFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.getTokenFromCookie(request);
        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUserName(token);
            String role = jwtProvider.getUserRole(token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" +role)));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("anonymous", null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
