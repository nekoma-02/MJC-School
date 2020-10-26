package com.epam.esm.service.exception;

public class GiftCertificateNotFoundException extends RuntimeException {

    private long id;
    private String message;

    public GiftCertificateNotFoundException() {}

    public GiftCertificateNotFoundException(long id, String message) {
        this.id = id;
        this.message = message;
    }


    public long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
