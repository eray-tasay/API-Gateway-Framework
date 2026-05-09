package com.eraytasay.service.discovery.exception;

public class NoSuchServiceException extends RuntimeException {
    public NoSuchServiceException(String message)
    {
        super(message);
    }
}
