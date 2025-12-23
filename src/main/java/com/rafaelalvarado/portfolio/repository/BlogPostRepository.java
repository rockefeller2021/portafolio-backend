package com.rafaelalvarado.portfolio.repository;

import com.rafaelalvarado.portfolio.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad BlogPost.
 * 
 * Proporciona métodos para realizar operaciones CRUD sobre posts del blog
 * y queries personalizadas para filtrado y ordenamiento.
 * 
 * @author Rafael Alvarado García
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    /**
     * Obtiene todos los posts publicados.
     * 
     * @return Lista de posts publicados
     */
    List<BlogPost> findByPublishedTrue();

    /**
     * Obtiene todos los posts publicados ordenados por fecha de creación
     * descendente.
     * 
     * @return Lista de posts publicados ordenados del más reciente al más antiguo
     */
    List<BlogPost> findByPublishedTrueOrderByCreatedAtDesc();

    /**
     * Obtiene todos los posts de una categoría específica.
     * 
     * @param category Categoría a filtrar
     * @return Lista de posts de la categoría especificada
     */
    List<BlogPost> findByCategory(String category);

    /**
     * Obtiene todos los posts publicados de una categoría específica.
     * 
     * @param category Categoría a filtrar
     * @return Lista de posts publicados de la categoría especificada
     */
    List<BlogPost> findByPublishedTrueAndCategory(String category);

    /**
     * Obtiene todos los posts publicados de una categoría ordenados por fecha.
     * 
     * @param category Categoría a filtrar
     * @return Lista de posts publicados de la categoría ordenados
     */
    List<BlogPost> findByPublishedTrueAndCategoryOrderByCreatedAtDesc(String category);

    /**
     * Obtiene todos los posts de un autor específico.
     * 
     * @param authorId ID del autor
     * @return Lista de posts del autor
     */
    List<BlogPost> findByAuthorId(Long authorId);

    /**
     * Cuenta el número de posts publicados.
     * 
     * @return Número de posts publicados
     */
    long countByPublishedTrue();
}
