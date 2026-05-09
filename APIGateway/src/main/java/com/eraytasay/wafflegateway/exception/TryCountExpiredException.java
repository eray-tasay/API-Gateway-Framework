package com.eraytasay.wafflegateway.exception;

public class TryCountExpiredException extends RuntimeException {
    public TryCountExpiredException(String message)
    {
        super(message);
    }

    public TryCountExpiredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
