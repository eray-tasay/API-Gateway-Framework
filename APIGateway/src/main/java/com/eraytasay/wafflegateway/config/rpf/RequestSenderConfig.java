package com.eraytasay.wafflegateway.config.rpf;

import com.eraytasay.wafflegateway.rpf.filter.chain.RequestSender;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class RequestSenderConfig {
    private final RestClient m_restClient;

    public RequestSenderConfig(RestClient restClient)
    {
        m_restClient = restClient;
    }

    @Bean
    public RequestSender requestSender()
    {
        return new RequestSender(m_restClient);
    }
}
