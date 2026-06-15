package com.eraytasay.service.discovery.config;

import com.eraytasay.service.discovery.heartbeating.HeartBeatingRequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@ConditionalOnProperty(prefix = "heart-beating", name = "enabled", havingValue = "true", matchIfMissing = true)
public class HeartBeatingConfig {
    private final RestClient m_restClient;

    public HeartBeatingConfig(RestClient restClient)
    {
        m_restClient = restClient;
    }

    @Value("${service.name}")
    private String m_serviceName;

    @Value("${server.port}")
    private int m_servicePort;

    @Value("${service-discovery.url}")
    private String m_url;

    @Value("${service-discovery.listen.path}")
    private String m_listenPath;

    @Bean
    public HeartBeatingRequestSender heartBeatingRequestSender()
    {
        return HeartBeatingRequestSender
                .builder()
                .serviceDiscoveryUrl(m_url)
                .serviceName(m_serviceName)
                .servicePort(m_servicePort)
                .listenPath(m_listenPath)
                .restClient(m_restClient)
                .build();
    }
}
