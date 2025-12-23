package com.rafaelalvarado.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO para crear un nuevo post del blog.
 * 
 * Este DTO se utiliza para recibir los datos necesarios
 * para crear un nuevo post desde el panel de administración.
 * 
 * @author Rafael Alvarado García
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {

    /**
     * Título del post
     */
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
    private String title;

    /**
     * Extracto del post
     */
    @NotBlank(message = "El extracto es obligatorio")
    @Size(min = 10, max = 500, message = "El extracto debe tener entre 10 y 500 caracteres")
    private String excerpt;

    /**
     * Contenido completo del post
     */
    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    /**
     * Categoría del post
     */
    @NotBlank(message = "La categoría es obligatoria")
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
     * Indica si se debe publicar inmediatamente
     */
    @Builder.Default
    private Boolean published = false;
}
