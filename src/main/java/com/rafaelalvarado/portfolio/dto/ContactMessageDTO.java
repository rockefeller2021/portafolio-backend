package com.rafaelalvarado.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar un mensaje de contacto en las respuestas de la API.
 * 
 * Este DTO se utiliza para enviar información de mensajes de contacto
 * al cliente (principalmente para el panel de administración).
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessageDTO {

    /**
     * ID del mensaje
     */
    private Long id;

    /**
     * Nombre del remitente
     */
    private String name;

    /**
     * Email del remitente
     */
    private String email;

    /**
     * Asunto del mensaje
     */
    private String subject;

    /**
     * Contenido del mensaje
     */
    private String message;

    /**
     * Indica si ha sido leído
     */
    private Boolean isRead;

    /**
     * Fecha de recepción
     */
    private LocalDateTime createdAt;
}
