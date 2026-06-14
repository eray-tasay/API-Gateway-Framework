package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.unregister.AutoServiceUnregistrar;
import com.eraytasay.service.discovery.unregister.ServiceUnregistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class ServiceUnregistrarConfig {
    private final RestClient m_restClient;

    public ServiceUnregistrarConfig(RestClient restClient)
    {
        m_restClient = restClient;
    }

    @Value("${service.name}")
    private String m_serviceName;

    @Value("${server.port}")
    private int m_servicePort;

    @Value("${service-discovery.url}")
    private String m_url;

    @Value("${service-discovery.unregister.path}")
    private String m_unregisterPath;

    @Bean
    public ServiceUnregistrar serviceUnregistrar()
    {
        return ServiceUnregistrar
                .builder()
                .serviceDiscoveryUrl(m_url)
                .serviceName(m_serviceName)
                .unregisterPath(m_unregisterPath)
                .restClient(m_restClient)
                .servicePort(m_servicePort)
                .build();
    }

    @Bean
    public AutoServiceUnregistrar autoServiceUnregistrar()
    {
        return new AutoServiceUnregistrar(serviceUnregistrar());
    }
}
