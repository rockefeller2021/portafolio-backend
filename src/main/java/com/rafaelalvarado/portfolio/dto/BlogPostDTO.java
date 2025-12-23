package com.rafaelalvarado.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representar un post del blog en las respuestas de la API.
 * 
 * Este DTO se utiliza para enviar información de posts al cliente,
 * incluyendo información básica del autor.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostDTO {

    /**
     * ID del post
     */
    private Long id;

    /**
     * Título del post
     */
    private String title;

    /**
     * Extracto del post
     */
    private String excerpt;

    /**
     * Contenido completo del post
     */
    private String content;

    /**
     * Categoría del post
     */
    private String category;

    /**
     * Tags del post
     */
    private List<String> tags;

    /**
     * Tiempo de lectura estimado
     */
    private String readTime;

    /**
     * Indica si está publicado
     */
    private Boolean published;

    /**
     * Nombre del autor
     */
    private String authorName;

    /**
     * ID del autor
     */
    private Long authorId;

    /**
     * Fecha de creación
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización
     */
    private LocalDateTime updatedAt;
}
