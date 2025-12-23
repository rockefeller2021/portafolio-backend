package com.rafaelalvarado.portfolio.repository;

import com.rafaelalvarado.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User.
 * 
 * Proporciona métodos para realizar operaciones CRUD sobre usuarios
 * y queries personalizadas para búsqueda por username y email.
 * 
 * @author Rafael Alvarado García
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su email.
     * 
     * @param email Email a buscar
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     * 
     * @param username Nombre de usuario a verificar
     * @return true si existe, false si no
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email dado.
     * 
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);
}
