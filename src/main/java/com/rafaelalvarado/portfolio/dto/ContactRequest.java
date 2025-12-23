package com.rafaelalvarado.portfolio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO para enviar un mensaje de contacto.
 * 
 * Este DTO se utiliza para recibir los datos del formulario
 * de contacto desde el cliente Angular.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequest {

    /**
     * Nombre del remitente
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    /**
     * Email del remitente
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    /**
     * Asunto del mensaje
     */
    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 5, max = 200, message = "El asunto debe tener entre 5 y 200 caracteres")
    private String subject;

    /**
     * Contenido del mensaje
     */
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 2000, message = "El mensaje debe tener entre 10 y 2000 caracteres")
    private String message;
}
