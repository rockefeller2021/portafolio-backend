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
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad User - Representa un usuario administrador del sistema.
 * 
 * Esta entidad almacena la información de los usuarios que pueden
 * autenticarse en el sistema para gestionar el blog y ver mensajes de contacto.
 * Las contraseñas se almacenan encriptadas con BCrypt.
 * 
 * @author Rafael Alvarado García
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Identificador único del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para login
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Email único del usuario
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Contraseña encriptada con BCrypt
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario (ADMIN, USER)
     */
    @Column(nullable = false, length = 20)
    @Builder.Default
    private String role = "ADMIN";

    /**
     * Fecha y hora de creación del usuario
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Posts del blog creados por este usuario
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BlogPost> posts = new ArrayList<>();

    /**
     * Agrega un post a la lista de posts del usuario
     * 
     * @param post Post a agregar
     */
    public void addPost(BlogPost post) {
        posts.add(post);
        post.setAuthor(this);
    }

    /**
     * Remueve un post de la lista de posts del usuario
     * 
     * @param post Post a remover
     */
    public void removePost(BlogPost post) {
        posts.remove(post);
        post.setAuthor(null);
    }
}
