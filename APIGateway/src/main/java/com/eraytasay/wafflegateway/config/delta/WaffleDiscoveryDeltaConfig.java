package com.eraytasay.wafflegateway.config.delta;

import com.eraytasay.wafflegateway.discovery.waffle.client.WaffleDeltaServiceDiscoveryClient;
import com.eraytasay.wafflegateway.discovery.waffle.response.handler.WaffleDeltaQueryResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ConditionalOnProperty(prefix = "api-gateway.delta", name = "enabled", havingValue = "true")
@ConditionalOnProperty(prefix = "api-gateway.service-discovery", name = "enabled", havingValue = "true")
public class WaffleDiscoveryDeltaConfig {
    private final RestClient m_restClient;

    public WaffleDiscoveryDeltaConfig(RestClient restClient)
    {
        m_restClient = restClient;
    }

    @Value("${api-gateway.service-discovery.url}")
    private String m_discoveryUrl;

    @Value("${api-gateway.service-discovery.delta-endpoint}")
    private String m_deltaEndpoint;

    @Bean
    public WaffleDeltaServiceDiscoveryClient deltaClient()
    {
        var client = new WaffleDeltaServiceDiscoveryClient();

        client.setRestClient(m_restClient);
        client.setUrl(m_discoveryUrl + m_deltaEndpoint);

        return client;
    }

    @Bean
    public WaffleDeltaQueryResponseHandler deltaResponseHandler()
    {
        return new WaffleDeltaQueryResponseHandler();
    }
}
