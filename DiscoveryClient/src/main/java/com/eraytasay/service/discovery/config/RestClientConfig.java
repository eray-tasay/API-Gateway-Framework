package com.eraytasay.service.discovery.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
public class RestClientConfig {
    @Bean
    public RestClient restClient()
    {
        return RestClient.create();
    }
}
