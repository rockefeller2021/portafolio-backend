package com.rafaelalvarado.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un usuario existente.
 * Todos los campos son opcionales.
 * 
 * @author Rafael Alvarado García
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "El email debe ser válido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private String role; // ADMIN o USER
}
