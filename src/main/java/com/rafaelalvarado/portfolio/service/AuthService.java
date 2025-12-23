package com.rafaelalvarado.portfolio.service;

import com.rafaelalvarado.portfolio.dto.LoginRequest;
import com.rafaelalvarado.portfolio.dto.LoginResponse;
import com.rafaelalvarado.portfolio.dto.UserDTO;
import com.rafaelalvarado.portfolio.entity.User;
import com.rafaelalvarado.portfolio.exception.AuthenticationException;
import com.rafaelalvarado.portfolio.repository.UserRepository;
import com.rafaelalvarado.portfolio.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autenticación.
 * 
 * Este servicio maneja la lógica de negocio relacionada con la autenticación
 * de usuarios, incluyendo login y generación de tokens JWT.
 * 
 * @author Rafael Alvarado García
 */
@Service
@Transactional
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Autentica un usuario y genera un token JWT.
     * 
     * @param request Credenciales de login
     * @return LoginResponse con token JWT y datos del usuario
     * @throws AuthenticationException Si las credenciales son inválidas
     */
    public LoginResponse login(LoginRequest request) {
        logger.info("Intento de login para usuario: {}", request.getUsername());

        // Buscar usuario por username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Credenciales inválidas"));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Contraseña incorrecta para usuario: {}", request.getUsername());
            throw new AuthenticationException("Credenciales inválidas");
        }

        // Generar token JWT
        String token = tokenProvider.generateToken(user.getUsername());

        logger.info("Login exitoso para usuario: {}", user.getUsername());

        // Construir respuesta
        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .user(mapToDTO(user))
                .build();
    }

    /**
     * Convierte una entidad User a UserDTO.
     * 
     * @param user Entidad User
     * @return UserDTO
     */
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
