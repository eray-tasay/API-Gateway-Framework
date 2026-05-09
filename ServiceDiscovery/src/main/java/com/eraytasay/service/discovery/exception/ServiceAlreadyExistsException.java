package com.eraytasay.service.discovery.exception;

public class ServiceAlreadyExistsException extends RuntimeException {
    public ServiceAlreadyExistsException(String message)
    {
        super(message);
    }
}
