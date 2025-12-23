package com.rafaelalvarado.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de autenticación JWT.
 * 
 * Este filtro intercepta todas las peticiones HTTP y valida el token JWT
 * presente en el header Authorization. Si el token es válido, establece
 * la autenticación en el SecurityContext.
 * 
 * @author Rafael Alvarado García
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Filtra las peticiones HTTP para validar el token JWT.
     * 
     * @param request     Petición HTTP
     * @param response    Respuesta HTTP
     * @param filterChain Cadena de filtros
     * @throws ServletException Si ocurre un error de servlet
     * @throws IOException      Si ocurre un error de I/O
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String username = tokenProvider.getUsernameFromToken(jwt);

                // Crear autenticación con rol ADMIN
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Usuario autenticado: {}", username);
            }
        } catch (Exception ex) {
            logger.error("No se pudo establecer la autenticación del usuario: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del header Authorization.
     * 
     * @param request Petición HTTP
     * @return Token JWT o null si no está presente
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
