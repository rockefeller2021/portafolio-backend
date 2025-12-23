package com.rafaelalvarado.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar un usuario en las respuestas de la API.
 * 
 * Este DTO se utiliza para enviar información del usuario al cliente
 * sin exponer datos sensibles como la contraseña.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    /**
     * ID del usuario
     */
    private Long id;

    /**
     * Nombre de usuario
     */
    private String username;

    /**
     * Email del usuario
     */
    private String email;

    /**
     * Rol del usuario
     */
    private String role;

    /**
     * Fecha de creación
     */
    private LocalDateTime createdAt;
}
