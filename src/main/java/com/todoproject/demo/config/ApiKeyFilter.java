package com.todoproject.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final String validApiKey;
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);

    public ApiKeyFilter(String validApiKey) {
        this.validApiKey = validApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        // 1. Obtener token de headers o cookies
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("API_KEY".equals(cookie.getName())) {
                        apiKey = cookie.getValue();
                        break;
                    }
                }
            }
        }

        logger.info("Ruta solicitada: {}", request.getRequestURI());
        logger.info("Token recibido: {}", apiKey);

        // Rutas públicas actualizadas
        String path = request.getRequestURI();
        if (path.equals("/") ||
            path.equals("/welcome") ||
            path.equals("/login") ||
            path.equals("/error") ||
            path.equals("/favicon.ico") ||
            path.startsWith("/css") ||
            path.startsWith("/img") ||
            path.startsWith("/js")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Validación de token
        if (apiKey != null && validApiKey.equals(apiKey)) {
            // Crear autenticación
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    "api-user",
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        } else {
            logger.warn("Acceso denegado. Token inválido o ausente");
            response.sendRedirect("/login");
        }
    }

}