package com.rafaelalvarado.portfolio.repository;

import com.rafaelalvarado.portfolio.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad ContactMessage.
 * 
 * Proporciona métodos para realizar operaciones CRUD sobre mensajes de contacto
 * y queries personalizadas para filtrado por estado de lectura.
 * 
 * @author Rafael Alvarado García
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    /**
     * Obtiene todos los mensajes no leídos.
     * 
     * @return Lista de mensajes no leídos
     */
    List<ContactMessage> findByIsReadFalse();

    /**
     * Obtiene todos los mensajes leídos.
     * 
     * @return Lista de mensajes leídos
     */
    List<ContactMessage> findByIsReadTrue();

    /**
     * Obtiene todos los mensajes ordenados por fecha de creación descendente.
     * 
     * @return Lista de mensajes ordenados del más reciente al más antiguo
     */
    List<ContactMessage> findAllByOrderByCreatedAtDesc();

    /**
     * Obtiene mensajes no leídos ordenados por fecha.
     * 
     * @return Lista de mensajes no leídos ordenados
     */
    List<ContactMessage> findByIsReadFalseOrderByCreatedAtDesc();

    /**
     * Cuenta el número de mensajes no leídos.
     * 
     * @return Número de mensajes no leídos
     */
    long countByIsReadFalse();
}
