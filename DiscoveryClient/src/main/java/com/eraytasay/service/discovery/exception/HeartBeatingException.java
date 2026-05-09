package com.eraytasay.service.discovery.exception;

public class HeartBeatingException extends RuntimeException {
    public HeartBeatingException(String message)
    {
        super(message);
    }

    public HeartBeatingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
