package com.rafaelalvarado.portfolio.controller;

import com.rafaelalvarado.portfolio.dto.CreateUserRequest;
import com.rafaelalvarado.portfolio.dto.UpdateUserRequest;
import com.rafaelalvarado.portfolio.dto.UserDTO;
import com.rafaelalvarado.portfolio.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de usuarios.
 * 
 * Proporciona endpoints para operaciones CRUD de usuarios.
 * Todos los endpoints requieren autenticación y rol ADMIN.
 * 
 * @author Rafael Alvarado García
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los usuarios.
     * 
     * @return Lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("GET /api/users - Listar todos los usuarios");
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtiene un usuario por ID.
     * 
     * @param id ID del usuario
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("GET /api/users/{} - Obtener usuario", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Crea un nuevo usuario.
     * 
     * @param request Datos del usuario a crear
     * @return Usuario creado
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        logger.info("POST /api/users - Crear usuario: {}", request.getUsername());
        UserDTO user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param id      ID del usuario a actualizar
     * @param request Datos de actualización
     * @return Usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        logger.info("PUT /api/users/{} - Actualizar usuario", id);
        UserDTO user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    /**
     * Elimina un usuario.
     * 
     * @param id ID del usuario a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/{} - Eliminar usuario", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
