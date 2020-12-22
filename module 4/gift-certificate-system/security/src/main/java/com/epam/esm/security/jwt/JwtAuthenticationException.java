package com.epam.esm.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends RuntimeException {

    private long id;
    private String message;
    private String entityName;

    public JwtAuthenticationException() {
        super();
    }

    public JwtAuthenticationException(String message) {
        this.message = message;
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public JwtAuthenticationException(Throwable cause) {
        super(cause);
    }

    public JwtAuthenticationException(String message, long id) {
        this.message = message;
        this.id = id;
    }

    public JwtAuthenticationException(String message, String entityName) {
        this.message = message;
        this.entityName = entityName;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
