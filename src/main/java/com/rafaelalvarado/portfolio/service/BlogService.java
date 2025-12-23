package com.rafaelalvarado.portfolio.service;

import com.rafaelalvarado.portfolio.dto.BlogPostDTO;
import com.rafaelalvarado.portfolio.dto.CreatePostRequest;
import com.rafaelalvarado.portfolio.dto.UpdatePostRequest;
import com.rafaelalvarado.portfolio.entity.BlogPost;
import com.rafaelalvarado.portfolio.entity.User;
import com.rafaelalvarado.portfolio.exception.ResourceNotFoundException;
import com.rafaelalvarado.portfolio.repository.BlogPostRepository;
import com.rafaelalvarado.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de posts del blog.
 * 
 * Este servicio maneja la lógica de negocio relacionada con los posts del blog,
 * incluyendo CRUD completo, filtrado por categoría y gestión de publicación.
 * 
 * @author Rafael Alvarado García
 */
@Service
@Transactional
public class BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Obtiene todos los posts publicados ordenados por fecha.
     * 
     * @return Lista de posts publicados
     */
    @Transactional(readOnly = true)
    public List<BlogPostDTO> getAllPublishedPosts() {
        logger.info("Obteniendo todos los posts publicados");
        return blogPostRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un post por su ID.
     * 
     * @param id ID del post
     * @return BlogPostDTO
     * @throws ResourceNotFoundException Si el post no existe
     */
    @Transactional(readOnly = true)
    public BlogPostDTO getPostById(Long id) {
        logger.info("Obteniendo post con ID: {}", id);
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con ID: " + id));
        return mapToDTO(post);
    }

    /**
     * Obtiene posts publicados filtrados por categoría.
     * 
     * @param category Categoría a filtrar
     * @return Lista de posts de la categoría
     */
    @Transactional(readOnly = true)
    public List<BlogPostDTO> getPostsByCategory(String category) {
        logger.info("Obteniendo posts de la categoría: {}", category);
        return blogPostRepository.findByPublishedTrueAndCategoryOrderByCreatedAtDesc(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo post del blog.
     * 
     * @param request  Datos del post a crear
     * @param username Nombre del usuario autor
     * @return BlogPostDTO del post creado
     * @throws ResourceNotFoundException Si el usuario no existe
     */
    public BlogPostDTO createPost(CreatePostRequest request, String username) {
        logger.info("Creando nuevo post: {}", request.getTitle());

        // Buscar usuario autor
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        // Crear post
        BlogPost post = BlogPost.builder()
                .title(request.getTitle())
                .excerpt(request.getExcerpt())
                .content(request.getContent())
                .category(request.getCategory())
                .tags(request.getTags())
                .readTime(request.getReadTime())
                .published(request.getPublished())
                .author(author)
                .build();

        BlogPost savedPost = blogPostRepository.save(post);
        logger.info("Post creado exitosamente con ID: {}", savedPost.getId());

        return mapToDTO(savedPost);
    }

    /**
     * Actualiza un post existente.
     * 
     * @param id      ID del post a actualizar
     * @param request Datos de actualización
     * @return BlogPostDTO del post actualizado
     * @throws ResourceNotFoundException Si el post no existe
     */
    public BlogPostDTO updatePost(Long id, UpdatePostRequest request) {
        logger.info("Actualizando post con ID: {}", id);

        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado con ID: " + id));

        // Actualizar solo los campos que vienen en el request
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getExcerpt() != null) {
            post.setExcerpt(request.getExcerpt());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getCategory() != null) {
            post.setCategory(request.getCategory());
        }
        if (request.getTags() != null) {
            post.setTags(request.getTags());
        }
        if (request.getReadTime() != null) {
            post.setReadTime(request.getReadTime());
        }
        if (request.getPublished() != null) {
            post.setPublished(request.getPublished());
        }

        BlogPost updatedPost = blogPostRepository.save(post);
        logger.info("Post actualizado exitosamente");

        return mapToDTO(updatedPost);
    }

    /**
     * Elimina un post.
     * 
     * @param id ID del post a eliminar
     * @throws ResourceNotFoundException Si el post no existe
     */
    public void deletePost(Long id) {
        logger.info("Eliminando post con ID: {}", id);

        if (!blogPostRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post no encontrado con ID: " + id);
        }

        blogPostRepository.deleteById(id);
        logger.info("Post eliminado exitosamente");
    }

    /**
     * Convierte una entidad BlogPost a BlogPostDTO.
     * 
     * @param post Entidad BlogPost
     * @return BlogPostDTO
     */
    private BlogPostDTO mapToDTO(BlogPost post) {
        return BlogPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .excerpt(post.getExcerpt())
                .content(post.getContent())
                .category(post.getCategory())
                .tags(post.getTags())
                .readTime(post.getReadTime())
                .published(post.getPublished())
                .authorName(post.getAuthor().getUsername())
                .authorId(post.getAuthor().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
