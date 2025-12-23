package com.rafaelalvarado.portfolio.service;

import com.rafaelalvarado.portfolio.dto.ContactMessageDTO;
import com.rafaelalvarado.portfolio.dto.ContactRequest;
import com.rafaelalvarado.portfolio.entity.ContactMessage;
import com.rafaelalvarado.portfolio.exception.ResourceNotFoundException;
import com.rafaelalvarado.portfolio.repository.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de mensajes de contacto.
 * 
 * Este servicio maneja la lógica de negocio relacionada con los mensajes
 * de contacto, incluyendo guardado, listado y marcado como leído.
 * 
 * @author Rafael Alvarado García
 */
@Service
@Transactional
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    /**
     * Guarda un nuevo mensaje de contacto.
     * 
     * @param request Datos del mensaje
     * @return ContactMessageDTO del mensaje guardado
     */
    public ContactMessageDTO saveMessage(ContactRequest request) {
        logger.info("Guardando mensaje de contacto de: {}", request.getEmail());

        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .isRead(false)
                .build();

        ContactMessage savedMessage = contactMessageRepository.save(message);
        logger.info("Mensaje de contacto guardado con ID: {}", savedMessage.getId());

        return mapToDTO(savedMessage);
    }

    /**
     * Obtiene todos los mensajes ordenados por fecha.
     * 
     * @return Lista de todos los mensajes
     */
    @Transactional(readOnly = true)
    public List<ContactMessageDTO> getAllMessages() {
        logger.info("Obteniendo todos los mensajes de contacto");
        return contactMessageRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene solo los mensajes no leídos.
     * 
     * @return Lista de mensajes no leídos
     */
    @Transactional(readOnly = true)
    public List<ContactMessageDTO> getUnreadMessages() {
        logger.info("Obteniendo mensajes no leídos");
        return contactMessageRepository.findByIsReadFalseOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Marca un mensaje como leído.
     * 
     * @param id ID del mensaje
     * @return ContactMessageDTO del mensaje actualizado
     * @throws ResourceNotFoundException Si el mensaje no existe
     */
    public ContactMessageDTO markAsRead(Long id) {
        logger.info("Marcando mensaje {} como leído", id);

        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado con ID: " + id));

        message.markAsRead();
        ContactMessage updatedMessage = contactMessageRepository.save(message);

        logger.info("Mensaje marcado como leído");
        return mapToDTO(updatedMessage);
    }

    /**
     * Obtiene el número de mensajes no leídos.
     * 
     * @return Número de mensajes no leídos
     */
    @Transactional(readOnly = true)
    public long getUnreadCount() {
        return contactMessageRepository.countByIsReadFalse();
    }

    /**
     * Convierte una entidad ContactMessage a ContactMessageDTO.
     * 
     * @param message Entidad ContactMessage
     * @return ContactMessageDTO
     */
    private ContactMessageDTO mapToDTO(ContactMessage message) {
        return ContactMessageDTO.builder()
                .id(message.getId())
                .name(message.getName())
                .email(message.getEmail())
                .subject(message.getSubject())
                .message(message.getMessage())
                .isRead(message.getIsRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
