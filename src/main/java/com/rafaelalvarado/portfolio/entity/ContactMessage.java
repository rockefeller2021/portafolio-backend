package com.rafaelalvarado.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad ContactMessage - Representa un mensaje de contacto enviado desde el
 * formulario.
 * 
 * Esta entidad almacena los mensajes que los visitantes envían a través
 * del formulario de contacto del portafolio. Incluye un flag para marcar
 * los mensajes como leídos.
 * 
 * @author Rafael Alvarado García
 */
@Entity
@Table(name = "contact_messages", indexes = {
        @Index(name = "idx_read", columnList = "isRead"),
        @Index(name = "idx_created_at", columnList = "createdAt")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessage {

    /**
     * Identificador único del mensaje
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la persona que envía el mensaje
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Email de contacto
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, length = 100)
    private String email;

    /**
     * Asunto del mensaje
     */
    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 5, max = 200, message = "El asunto debe tener entre 5 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String subject;

    /**
     * Contenido del mensaje
     */
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 2000, message = "El mensaje debe tener entre 10 y 2000 caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * Indica si el mensaje ha sido leído por el administrador
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    /**
     * Fecha y hora en que se recibió el mensaje
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Marca el mensaje como leído
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * Marca el mensaje como no leído
     */
    public void markAsUnread() {
        this.isRead = false;
    }
}
