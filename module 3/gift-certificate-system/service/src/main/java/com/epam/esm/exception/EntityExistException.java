package com.epam.esm.exception;

public class EntityExistException extends RuntimeException {
    private long id;
    private String message;
    private String entityName;

    public EntityExistException() {
        super();
    }

    public EntityExistException(String message) {
        this.message = message;
    }

    public EntityExistException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public EntityExistException(Throwable cause) {
        super(cause);
    }

    public EntityExistException(String message, long id) {
        this.message = message;
        this.id = id;
    }

    public EntityExistException(String message, String entityName) {
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
