package com.rafaelalvarado.portfolio.controller;

import com.rafaelalvarado.portfolio.dto.BlogPostDTO;
import com.rafaelalvarado.portfolio.dto.CreatePostRequest;
import com.rafaelalvarado.portfolio.dto.UpdatePostRequest;
import com.rafaelalvarado.portfolio.service.BlogService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de posts del blog.
 * 
 * Este controlador expone endpoints para CRUD de posts del blog.
 * Los endpoints de lectura son públicos, los de escritura requieren
 * autenticación.
 * 
 * @author Rafael Alvarado García
 */
@RestController
@RequestMapping("/blog/posts")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    /**
     * Obtiene todos los posts publicados.
     * 
     * Endpoint público.
     * 
     * @return Lista de posts publicados
     */
    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAllPublishedPosts() {
        logger.info("GET /api/blog/posts");
        List<BlogPostDTO> posts = blogService.getAllPublishedPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Obtiene un post por su ID.
     * 
     * Endpoint público.
     * 
     * @param id ID del post
     * @return BlogPostDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getPostById(@PathVariable Long id) {
        logger.info("GET /api/blog/posts/{}", id);
        BlogPostDTO post = blogService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * Obtiene posts filtrados por categoría.
     * 
     * Endpoint público.
     * 
     * @param category Categoría a filtrar
     * @return Lista de posts de la categoría
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogPostDTO>> getPostsByCategory(@PathVariable String category) {
        logger.info("GET /api/blog/posts/category/{}", category);
        List<BlogPostDTO> posts = blogService.getPostsByCategory(category);
        return ResponseEntity.ok(posts);
    }

    /**
     * Crea un nuevo post.
     * 
     * Requiere autenticación JWT.
     * 
     * @param request        Datos del post a crear
     * @param authentication Información del usuario autenticado
     * @return BlogPostDTO del post creado
     */
    @PostMapping
    public ResponseEntity<BlogPostDTO> createPost(
            @Valid @RequestBody CreatePostRequest request,
            Authentication authentication) {
        logger.info("POST /api/blog/posts - Usuario: {}", authentication.getName());
        BlogPostDTO post = blogService.createPost(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /**
     * Actualiza un post existente.
     * 
     * Requiere autenticación JWT.
     * 
     * @param id      ID del post a actualizar
     * @param request Datos de actualización
     * @return BlogPostDTO del post actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDTO> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request) {
        logger.info("PUT /api/blog/posts/{}", id);
        BlogPostDTO post = blogService.updatePost(id, request);
        return ResponseEntity.ok(post);
    }

    /**
     * Elimina un post.
     * 
     * Requiere autenticación JWT.
     * 
     * @param id ID del post a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        logger.info("DELETE /api/blog/posts/{}", id);
        blogService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
