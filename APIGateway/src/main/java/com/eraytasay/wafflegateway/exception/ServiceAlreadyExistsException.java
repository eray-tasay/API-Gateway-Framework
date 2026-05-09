package com.eraytasay.wafflegateway.exception;

public class ServiceAlreadyExistsException extends RuntimeException {
    public ServiceAlreadyExistsException(String message)
    {
        super(message);
    }
}
