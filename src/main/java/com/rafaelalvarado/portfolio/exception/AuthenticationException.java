package com.rafaelalvarado.portfolio.exception;

/**
 * Excepción personalizada para errores de autenticación.
 * 
 * Esta excepción se lanza cuando las credenciales de un usuario son inválidas
 * o cuando ocurre un error durante el proceso de autenticación.
 * 
 * @author Rafael Alvarado García
 */
public class AuthenticationException extends RuntimeException {

    /**
     * Constructor con mensaje.
     * 
     * @param message Mensaje de error
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     * 
     * @param message Mensaje de error
     * @param cause   Causa de la excepción
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
