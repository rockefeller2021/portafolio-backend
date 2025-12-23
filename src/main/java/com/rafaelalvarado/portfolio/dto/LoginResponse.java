package com.rafaelalvarado.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO para autenticación exitosa.
 * 
 * Este DTO se envía al cliente después de un login exitoso,
 * conteniendo el token JWT y la información del usuario.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    /**
     * Token JWT para autenticación en requests subsecuentes
     */
    private String token;

    /**
     * Tipo de token (siempre "Bearer")
     */
    @Builder.Default
    private String type = "Bearer";

    /**
     * Información del usuario autenticado
     */
    private UserDTO user;
}
