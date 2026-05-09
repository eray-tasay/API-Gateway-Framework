package com.eraytasay.service.discovery.unregister;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class AutoServiceUnregistrar {
    private final ServiceUnregistrar m_serviceUnregistrar;

    public AutoServiceUnregistrar(ServiceUnregistrar serviceUnregistrar)
    {
        m_serviceUnregistrar = serviceUnregistrar;
    }

    @PreDestroy
    public void deleteOnShutdown()
    {
        m_serviceUnregistrar.unregister();
    }
}
