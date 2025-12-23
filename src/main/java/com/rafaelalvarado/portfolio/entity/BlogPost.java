package com.rafaelalvarado.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad BlogPost - Representa un post del blog técnico.
 * 
 * Esta entidad almacena toda la información de un artículo del blog,
 * incluyendo título, contenido, categoría, tags y metadata.
 * Los posts pueden estar publicados o en borrador.
 * 
 * @author Rafael Alvarado García
 */
@Entity
@Table(name = "blog_posts", indexes = {
        @Index(name = "idx_category", columnList = "category"),
        @Index(name = "idx_published", columnList = "published"),
        @Index(name = "idx_created_at", columnList = "createdAt")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost {

    /**
     * Identificador único del post
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título del post
     */
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * Extracto o resumen del post
     */
    @NotBlank(message = "El extracto es obligatorio")
    @Size(min = 10, max = 500, message = "El extracto debe tener entre 10 y 500 caracteres")
    @Column(nullable = false, length = 500)
    private String excerpt;

    /**
     * Contenido completo del post (puede ser HTML o Markdown)
     */
    @NotBlank(message = "El contenido es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * Categoría del post (Testing, Frontend, Backend, IA, etc.)
     */
    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 50)
    private String category;

    /**
     * Tags del post separados por comas
     */
    @ElementCollection
    @CollectionTable(name = "blog_post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    /**
     * Tiempo estimado de lectura (ej: "5 min", "10 min")
     */
    @Column(length = 20)
    private String readTime;

    /**
     * Indica si el post está publicado o es un borrador
     */
    @Column(nullable = false)
    @Builder.Default
    private Boolean published = false;

    /**
     * Autor del post
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * Fecha y hora de creación del post
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización del post
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Agrega un tag al post
     * 
     * @param tag Tag a agregar
     */
    public void addTag(String tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    /**
     * Remueve un tag del post
     * 
     * @param tag Tag a remover
     */
    public void removeTag(String tag) {
        if (tags != null) {
            tags.remove(tag);
        }
    }
}
