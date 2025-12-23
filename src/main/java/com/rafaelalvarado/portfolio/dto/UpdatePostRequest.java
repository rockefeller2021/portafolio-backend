package com.rafaelalvarado.portfolio.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO para actualizar un post del blog existente.
 * 
 * Este DTO se utiliza para recibir los datos de actualización
 * de un post desde el panel de administración.
 * Todos los campos son opcionales para permitir actualizaciones parciales.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {

    /**
     * Título del post (opcional)
     */
    @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
    private String title;

    /**
     * Extracto del post (opcional)
     */
    @Size(min = 10, max = 500, message = "El extracto debe tener entre 10 y 500 caracteres")
    private String excerpt;

    /**
     * Contenido completo del post (opcional)
     */
    private String content;

    /**
     * Categoría del post (opcional)
     */
    private String category;

    /**
     * Tags del post (opcional)
     */
    private List<String> tags;

    /**
     * Tiempo de lectura estimado (opcional)
     */
    private String readTime;

    /**
     * Estado de publicación (opcional)
     */
    private Boolean published;
}
