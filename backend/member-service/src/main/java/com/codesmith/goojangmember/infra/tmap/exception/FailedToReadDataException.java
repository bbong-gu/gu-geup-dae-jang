package com.codesmith.goojangmember.infra.tmap.exception;

public class FailedToReadDataException extends RuntimeException {
    public FailedToReadDataException(String message) {
        super(message);
    }

    public FailedToReadDataException(String message, Throwable cause) {
        super(message, cause);
    }
}