package com.vekrest.vekproducer.vekproducer.controller.advice.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
