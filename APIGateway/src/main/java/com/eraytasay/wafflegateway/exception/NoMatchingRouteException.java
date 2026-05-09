package com.eraytasay.wafflegateway.exception;

public class NoMatchingRouteException extends RuntimeException {
    public NoMatchingRouteException(String message)
    {
        super(message);
    }
}
