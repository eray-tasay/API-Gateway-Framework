package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.register.AutoServiceRegistrar;
import com.eraytasay.service.discovery.register.ServiceRegistrar;
import com.eraytasay.service.discovery.register.dto.LoadBalancing;
import com.eraytasay.service.discovery.register.handler.IRegistrationSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class ServiceRegistrarConfig {
    private final RestClient m_restClient;
    private final IRegistrationSuccessHandler m_registrationSuccessHandler;

    public ServiceRegistrarConfig(RestClient restClient, IRegistrationSuccessHandler registrationSuccessHandler)
    {
        m_restClient = restClient;
        m_registrationSuccessHandler = registrationSuccessHandler;
    }

    @Value("${service.name}")
    private String m_serviceName;

    @Value("${load-balancing-algorithm}")
    private LoadBalancing m_algorithm;

    @Value("${server.port}")
    private int m_servicePort;

    @Value("${service-discovery.url}")
    private String m_url;

    @Value("${service-discovery.register.path}")
    private String m_registerPath;

    @Bean
    public ServiceRegistrar serviceRegistrar()
    {
        return ServiceRegistrar
                .builder()
                .serviceDiscoveryUrl(m_url)
                .serviceName(m_serviceName)
                .registerPath(m_registerPath)
                .algorithm(m_algorithm)
                .restClient(m_restClient)
                .servicePort(m_servicePort)
                .successHandler(m_registrationSuccessHandler)
                .build();
    }

    @Bean
    public AutoServiceRegistrar autoServiceRegistrar()
    {
        return new AutoServiceRegistrar(serviceRegistrar());
    }
}
