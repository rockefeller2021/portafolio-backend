package com.rafaelalvarado.portfolio.exception;

/**
 * Excepción personalizada para recursos no encontrados.
 * 
 * Esta excepción se lanza cuando se intenta acceder a un recurso
 * que no existe en la base de datos.
 * 
 * @author Rafael Alvarado García
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * 
     * @param message Mensaje de error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * 
     * @param message Mensaje de error
     * @param cause   Causa de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
