package com.rafaelalvarado.portfolio.controller;

import com.rafaelalvarado.portfolio.dto.ContactMessageDTO;
import com.rafaelalvarado.portfolio.dto.ContactRequest;
import com.rafaelalvarado.portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de mensajes de contacto.
 * 
 * Este controlador expone endpoints para recibir mensajes de contacto
 * y gestionarlos (solo para administradores autenticados).
 * 
 * @author Rafael Alvarado García
 */
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    /**
     * Recibe un mensaje de contacto desde el formulario.
     * 
     * Endpoint público.
     * 
     * @param request Datos del mensaje
     * @return Respuesta de confirmación
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(@Valid @RequestBody ContactRequest request) {
        logger.info("POST /api/contact - Email: {}", request.getEmail());
        contactService.saveMessage(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mensaje recibido exitosamente");
        response.put("status", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene todos los mensajes de contacto.
     * 
     * Requiere autenticación JWT.
     * 
     * @return Lista de todos los mensajes
     */
    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessageDTO>> getAllMessages() {
        logger.info("GET /api/contact/messages");
        List<ContactMessageDTO> messages = contactService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Obtiene solo los mensajes no leídos.
     * 
     * Requiere autenticación JWT.
     * 
     * @return Lista de mensajes no leídos
     */
    @GetMapping("/messages/unread")
    public ResponseEntity<List<ContactMessageDTO>> getUnreadMessages() {
        logger.info("GET /api/contact/messages/unread");
        List<ContactMessageDTO> messages = contactService.getUnreadMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Marca un mensaje como leído.
     * 
     * Requiere autenticación JWT.
     * 
     * @param id ID del mensaje
     * @return Mensaje actualizado
     */
    @PutMapping("/messages/{id}/read")
    public ResponseEntity<ContactMessageDTO> markAsRead(@PathVariable Long id) {
        logger.info("PUT /api/contact/messages/{}/read", id);
        ContactMessageDTO message = contactService.markAsRead(id);
        return ResponseEntity.ok(message);
    }

    /**
     * Obtiene el contador de mensajes no leídos.
     * 
     * Requiere autenticación JWT.
     * 
     * @return Número de mensajes no leídos
     */
    @GetMapping("/messages/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        logger.info("GET /api/contact/messages/unread/count");
        long count = contactService.getUnreadCount();

        Map<String, Long> response = new HashMap<>();
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
}
