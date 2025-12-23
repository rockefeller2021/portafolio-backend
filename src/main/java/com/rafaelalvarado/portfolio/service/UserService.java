package com.rafaelalvarado.portfolio.service;

import com.rafaelalvarado.portfolio.dto.CreateUserRequest;
import com.rafaelalvarado.portfolio.dto.UpdateUserRequest;
import com.rafaelalvarado.portfolio.dto.UserDTO;
import com.rafaelalvarado.portfolio.entity.User;
import com.rafaelalvarado.portfolio.exception.ResourceNotFoundException;
import com.rafaelalvarado.portfolio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de usuarios.
 * 
 * Proporciona operaciones CRUD para usuarios del sistema.
 * 
 * @author Rafael Alvarado García
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios.
     * 
     * @return Lista de usuarios
     */
    public List<UserDTO> getAllUsers() {
        logger.info("Obteniendo todos los usuarios");
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por ID.
     * 
     * @param id ID del usuario
     * @return Usuario encontrado
     * @throws ResourceNotFoundException Si el usuario no existe
     */
    public UserDTO getUserById(Long id) {
        logger.info("Obteniendo usuario con ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return mapToDTO(user);
    }

    /**
     * Crea un nuevo usuario.
     * 
     * @param request Datos del usuario a crear
     * @return Usuario creado
     */
    public UserDTO createUser(CreateUserRequest request) {
        logger.info("Creando nuevo usuario: {}", request.getUsername());

        // Verificar si el username ya existe
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El username ya está en uso");
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        // Crear usuario
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        logger.info("Usuario creado exitosamente: {}", savedUser.getUsername());

        return mapToDTO(savedUser);
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param id      ID del usuario a actualizar
     * @param request Datos de actualización
     * @return Usuario actualizado
     * @throws ResourceNotFoundException Si el usuario no existe
     */
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        logger.info("Actualizando usuario con ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Actualizar email si se proporciona
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            // Verificar que el email no esté en uso por otro usuario
            if (userRepository.existsByEmail(request.getEmail()) &&
                    !user.getEmail().equals(request.getEmail())) {
                throw new IllegalArgumentException("El email ya está en uso");
            }
            user.setEmail(request.getEmail());
        }

        // Actualizar contraseña si se proporciona
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Actualizar rol si se proporciona
        if (request.getRole() != null && !request.getRole().isEmpty()) {
            user.setRole(request.getRole());
        }

        User updatedUser = userRepository.save(user);
        logger.info("Usuario actualizado exitosamente: {}", updatedUser.getUsername());

        return mapToDTO(updatedUser);
    }

    /**
     * Elimina un usuario.
     * 
     * @param id ID del usuario a eliminar
     * @throws ResourceNotFoundException Si el usuario no existe
     */
    public void deleteUser(Long id) {
        logger.info("Eliminando usuario con ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }

        userRepository.deleteById(id);
        logger.info("Usuario eliminado exitosamente con ID: {}", id);
    }

    /**
     * Convierte una entidad User a UserDTO.
     * 
     * @param user Entidad User
     * @return UserDTO
     */
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
