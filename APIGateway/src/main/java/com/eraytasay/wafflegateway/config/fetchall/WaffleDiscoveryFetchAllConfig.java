package com.eraytasay.wafflegateway.config.fetchall;

import com.eraytasay.wafflegateway.discovery.waffle.client.WaffleFetchAllServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.handler.WaffleFetchAllQueryResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class WaffleDiscoveryFetchAllConfig {
    private final RestClient m_restClient;

    public WaffleDiscoveryFetchAllConfig(RestClient restClient)
    {
        m_restClient = restClient;
    }

    @Value("${api-gateway.service-discovery.url}")
    private String m_discoveryUrl;

    @Value("${api-gateway.service-discovery.services-endpoint}")
    private String m_servicesEndpoint;

    @Bean
    public WaffleFetchAllServiceDiscoveryClient fetchAllClient()
    {
        var client = new WaffleFetchAllServiceDiscoveryClient();

        client.setRestClient(m_restClient);
        client.setUrl(m_discoveryUrl + m_servicesEndpoint);

        return client;
    }

    @Bean
    public WaffleFetchAllQueryResponseHandler fetchAllResponseHandler()
    {
        return new WaffleFetchAllQueryResponseHandler();
    }
}
