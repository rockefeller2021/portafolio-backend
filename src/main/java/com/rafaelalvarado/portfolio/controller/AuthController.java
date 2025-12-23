package com.rafaelalvarado.portfolio.controller;

import com.rafaelalvarado.portfolio.dto.LoginRequest;
import com.rafaelalvarado.portfolio.dto.LoginResponse;
import com.rafaelalvarado.portfolio.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación.
 * 
 * Este controlador expone endpoints para login y autenticación de usuarios.
 * 
 * @author Rafael Alvarado García
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * Endpoint de login.
     * 
     * Autentica un usuario y retorna un token JWT.
     * 
     * @param request Credenciales de login
     * @return LoginResponse con token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("POST /api/auth/login - Usuario: {}", request.getUsername());
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
