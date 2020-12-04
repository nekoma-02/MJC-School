package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException{
    private long id;
    private String message;

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, long id) {
        this.message = message;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
