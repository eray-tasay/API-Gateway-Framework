package com.eraytasay.wafflegateway.exception;

public class ServiceDiscoveryClientException extends RuntimeException {
    public ServiceDiscoveryClientException(String message)
    {
        super(message);
    }

    public ServiceDiscoveryClientException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ServiceDiscoveryClientException(Throwable cause)
    {
        super(cause);
    }
}
