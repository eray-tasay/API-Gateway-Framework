package com.eraytasay.wafflegateway.exception;

public class NoSuchServiceException extends RuntimeException {
    public NoSuchServiceException(String message)
    {
        super(message);
    }
}
